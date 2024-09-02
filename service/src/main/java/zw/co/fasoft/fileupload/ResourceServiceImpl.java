package zw.co.fasoft.fileupload;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import zw.co.fasoft.NotificationServiceImpl;
import zw.co.fasoft.embeddables.ContributorDetails;
import zw.co.fasoft.exceptions.RecordNotFoundException;
import zw.co.fasoft.resourcecategories.ResourceCategoryService;
import zw.co.fasoft.resourcecategory.ResourceCategory;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.useraccount.UserAccountRepository;
import zw.co.fasoft.utils.Message;
import zw.co.fasoft.utils.enums.ResourceStatus;
import zw.co.fasoft.utils.enums.UserGroup;

import java.util.*;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final NotificationServiceImpl notificationService;
    private final UserAccountRepository userAccountRepository;
    private final ResourceCategoryService resourceCategoryService;

    @Override
    public List<Resource> create(List<ResourceRequest> request, String username) {
        // Find user account by username or throw an exception if not found
        var userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        // Use a HashSet to avoid duplicate resources
        Set<Resource> documents = new HashSet<>();

        // Process each document request
        request.stream()
                .filter(documentRequest -> !documentExists(documentRequest)) // Filter out existing documents
                .forEach(documentRequest -> {
                    // Create ContributorDetails from the request
                    ContributorDetails contributorDetails = ContributorDetails.builder()
                            .name(documentRequest.getContributorDetails().getName())
                            .email(documentRequest.getContributorDetails().getEmail())
                            .studentOrtStaffId(documentRequest.getContributorDetails().getStudentOrtStaffId())
                            .build();

                    // Initialize Resource object with an empty list for resourceCategory
                    Resource document = Resource.builder()
                            .title(documentRequest.getTitle())
                            .description(documentRequest.getDescription())
                            .keywords(documentRequest.getKeywords())
                            .status(ResourceStatus.AWAITING_APPROVAL)
                            .isDeleted(false)
                            .contributorDetails(contributorDetails)
                            .userAccount(userAccount)
                            .resourceCategory(new ArrayList<>()) // Initialize as an empty list
                            .uri(documentRequest.getUri())
                            .build();

                    // Add resource categories if provided
                    if (documentRequest.getResourceCategoryIds() != null) {
                        documentRequest.getResourceCategoryIds()
                                .forEach(resourceCategoryId -> {
                                    var resourceCategory = resourceCategoryService.byId(resourceCategoryId);
                                    if (Objects.nonNull(resourceCategory)) {
                                        document.getResourceCategory().add(resourceCategory);
                                    }
                                });
                    }

                    // Add the document to the set of documents
                    documents.add(document);

                    // Send a notification after adding the document
                    sendNotification(documentRequest, userAccount);
                });

        // Save all documents to the repository and flush
        List<Resource> savedResources = resourceRepository.saveAllAndFlush(documents);

        // Notify admin if there are any saved resources
        if (!savedResources.isEmpty()) {
            notifyAdmin(savedResources.size());
        }

        return savedResources;
    }

    @Async
    public void sendNotification(ResourceRequest request,UserAccount userAccount){
        String subject = "Submission Received: Your Academic Resource is Awaiting Approval";
        notificationService.sendNotification(Message.CONTRIBUTION_SUBMITTED_MESSAGE
                        .replace("{resourceTitle}", request.getTitle()),
                subject, request.getContributorDetails().getName(),
                userAccount, true, false, false);
    }

    @Async
    public void notifyAdmin(Integer count) {
        log.info("Total number of documents created: {}", count);

        List<UserAccount> userAccounts = userAccountRepository
                .findAllByUserGroupAndIsDeletedOrderByCreatedOnDesc(UserGroup.ADMIN,false,Pageable.unpaged()).getContent();
        userAccounts.forEach(userAccount -> {
            String content = Message.ADMIN_NEW_ACADEMIC_RESOURCE_MESSAGE.replace(
                    "{count}",
                    String.valueOf(count)
            );
            String subject = "NEW RESOURCES WAITING FOR APPROVAL ALERT";

            String recepientName = userAccount.getFullName();
            notificationService.sendNotification(content , subject, recepientName, userAccount, true, false, false);
        });

    }

    private boolean documentExists(ResourceRequest documentRequest) {
        if(resourceRepository.existsByTitle(documentRequest.getTitle())) {
            return true;
        }
        return false;
    }

    @Override
    public Resource update(Long id, ResourceRequest request) {
        var document = resourceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Resource not found"));

        document.setTitle(request.getTitle());
        document.setDescription(request.getDescription());
        document.setKeywords(request.getKeywords());
        document.setUri(request.getUri());
        return resourceRepository.save(document);
    }



    @Override
    public Page<Resource> searchForResources(String searchParam, Pageable pageable) {
        if(!resourceRepository.findAllByTitleContainingIgnoreCaseAndIsDeleted(searchParam,false,pageable).isEmpty()) {
            return resourceRepository.findAllByTitleContainingIgnoreCaseAndIsDeleted(searchParam,false,pageable);
        }
        if(!resourceRepository.findAllByDescriptionContainingIgnoreCaseAndIsDeleted(searchParam,false,pageable).isEmpty()) {
            return resourceRepository.findAllByDescriptionContainingIgnoreCaseAndIsDeleted(searchParam,false,pageable);
        }
        if(!resourceRepository.findAllByKeywordsContainingIgnoreCaseAndIsDeleted(searchParam,false,pageable).isEmpty()) {
            return resourceRepository.findAllByKeywordsContainingIgnoreCaseAndIsDeleted(searchParam,false,pageable);
        }
//        if(!resourceRepository.findAllByContributorDetails_NameContainingIgnoreCaseOrderByCreatedOnDesc(searchParam).isEmpty()) {
//            return resourceRepository.findAllByResourceCategory(categoryId);
//        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public Resource getById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Resource not found"));
    }

    @Override
    public void delete(Long id) {
        var document = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        document.setIsDeleted(true);
        resourceRepository.save(document);
    }

    @Override
    public Page<Resource> getAllResources(String contributorName, String title, String description, String keywords, Long categoryId, Pageable pageable) {
        ResourceCategory resourceCategory = null;
        if(Objects.nonNull(categoryId)){
            resourceCategory = resourceCategoryService.getResourceCategoryById(categoryId);
        }
        if(Objects.nonNull(title)) {
            return resourceRepository.findAllByTitleContainingIgnoreCaseAndIsDeleted(title,false,pageable);
        }
        if(Objects.nonNull(description)) {
            return resourceRepository.findAllByDescriptionContainingIgnoreCaseAndIsDeleted(description,false,pageable);
        }
        if(Objects.nonNull(keywords)) {
            return resourceRepository.findAllByKeywordsContainingIgnoreCaseAndIsDeleted(keywords,false,pageable);
        }
        if(Objects.nonNull(categoryId)) {
            assert resourceCategory != null;
            return resourceRepository.findAllByResourceCategoryAndIsDeleted(resourceCategory,false,pageable);
        }
        if(Objects.nonNull(contributorName)) {
            return resourceRepository.findAllByContributorDetails_NameContainingIgnoreCaseAndIsDeleted(contributorName,false,pageable);
        }
        return resourceRepository.findAllByIsDeleted(false,pageable);
    }

    @Override
    public Resource approve(Long resourceId) {
        var resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new RecordNotFoundException("Resource not found"));

        resource.setStatus(ResourceStatus.APPROVED);

        resourceRepository.save(resource);
        String subject = "Submission Received: Your Academic Resource has been Approved";
        notificationService.sendNotification(Message.CONTRIBUTION_APPROVED_MESSAGE
                        .replace("{resourceTitle}", resource.getTitle()),
                subject, resource.getContributorDetails().getName(),
                resource.getUserAccount(), true, false, false);

        notifyEveryone(resource);

        return resource;
    }

    @Async
    public void notifyEveryone(Resource resource) {
        String subject = "New Academic Resource Available on TCFL Online Resource Hub!";
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        userAccounts.forEach(userAccount -> {
            notificationService.sendNotification(Message.NEW_APPROVED_RESOURCE_MESSAGE
                    .replace("{title}", resource.getTitle())
                    .replace("{description}", resource.getDescription())
                    .replace("{resource-link}", resource.getUri())
                    .replace("{contributor-name}", resource.getKeywords()),
                    subject, resource.getContributorDetails().getName(),
                    userAccount, true, false, false);
        });
    }

    @Override
    public Resource reject(Long resourceId, String reason) {
        var resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new RecordNotFoundException("Resource not found"));

        resource.setStatus(ResourceStatus.REJECTED);
         resourceRepository.save(resource);

         String subject = "Submission Received: Your Academic Resource has been Rejected";
         notificationService.sendNotification(Message.CONTRIBUTION_REJECTED_MESSAGE
                         .replace("{resourceTitle}", resource.getTitle())
                         .replace("{reason}", reason),
                 subject, resource.getContributorDetails().getName(),
                 resource.getUserAccount(), true, false, false);
         return resource;
    }

    @Override
    public Page<Resource> getProfileResources(String username, Pageable pageable) {
//        if(Objects.nonNull(categoryId)) {
//            return resourceRepository.findAllByUserAccount_UsernameAndResourceCategoryOrderByCreatedOnDesc(username,categoryId);
//        }
        return resourceRepository.findAllByUserAccount_UsernameAndIsDeleted(username,false,pageable);
    }

    @Override
    public Page<Resource> searchResourcesForStudent(String searchParam, Pageable pageable) {
        if(!resourceRepository.findAllByTitleContainingIgnoreCaseAndStatusAndIsDeleted(searchParam,ResourceStatus.APPROVED,false,pageable).isEmpty()) {
            return resourceRepository.findAllByTitleContainingIgnoreCaseAndStatusAndIsDeleted(searchParam,ResourceStatus.APPROVED,false,pageable);
        }
        if(!resourceRepository.findAllByDescriptionContainingIgnoreCaseAndStatusAndIsDeleted(searchParam,ResourceStatus.APPROVED,false,pageable).isEmpty()) {
            return resourceRepository.findAllByDescriptionContainingIgnoreCaseAndStatusAndIsDeleted(searchParam,ResourceStatus.APPROVED,false,pageable);
        }
        if(!resourceRepository.findAllByKeywordsContainingIgnoreCaseAndStatusAndIsDeleted(searchParam,ResourceStatus.APPROVED,false,pageable).isEmpty()) {
            return resourceRepository.findAllByKeywordsContainingIgnoreCaseAndStatusAndIsDeleted(searchParam,ResourceStatus.APPROVED,false,pageable);
        }
//        if(!resourceRepository.findAllByContributorDetails_NameContainingIgnoreCaseOrderByCreatedOnDesc(searchParam).isEmpty()) {
//            return resourceRepository.findAllByResourceCategory(categoryId);
//        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public Page<Resource> getAllResourcesForStudent(String title, String description, String keywords, String contributorName, Long categoryId, Pageable pageable) {
        ResourceCategory resourceCategory = null;
        if(Objects.nonNull(categoryId)){
            resourceCategory = resourceCategoryService.getResourceCategoryById(categoryId);
        }
        if(Objects.nonNull(title)) {
            return resourceRepository.findAllByTitleContainingIgnoreCaseAndStatusAndIsDeleted(title,ResourceStatus.APPROVED,false,pageable);
        }
        if(Objects.nonNull(description)) {
            return resourceRepository.findAllByDescriptionContainingIgnoreCaseAndStatusAndIsDeleted(description,ResourceStatus.APPROVED,false,pageable);
        }
        if(Objects.nonNull(keywords)) {
            return resourceRepository.findAllByKeywordsContainingIgnoreCaseAndStatusAndIsDeleted(keywords,ResourceStatus.APPROVED,false,pageable);
        }
        if(Objects.nonNull(categoryId)) {
            assert resourceCategory != null;
            return resourceRepository.findAllByResourceCategoryAndStatusAndIsDeleted(resourceCategory,ResourceStatus.APPROVED,false,pageable);
        }
        if(Objects.nonNull(contributorName)) {
            return resourceRepository.findAllByContributorDetails_NameContainingIgnoreCaseAndStatusAndIsDeleted(contributorName,ResourceStatus.APPROVED,false, pageable);
        }
        return resourceRepository.findAllByStatusAndIsDeleted(ResourceStatus.APPROVED,false,pageable);
    }
}
