package zw.co.fasoft.fileupload;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import zw.co.fasoft.NotificationServiceImpl;
import zw.co.fasoft.embeddables.ContributorDetails;
import zw.co.fasoft.exceptions.RecordNotFoundException;
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

    @Override
    public List<Resource> create(List<ResourceRequest> request, String username) {
        var userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Set<Resource> documents = new HashSet<>();

        request.stream()
                .filter(documentRequest -> !documentExists(documentRequest))
                .forEach(documentRequest -> {
                    ContributorDetails contributorDetails = ContributorDetails.builder()
                            .name(documentRequest.getContributorDetails().getName())
                            .email(documentRequest.getContributorDetails().getEmail())
                            .studentOrtStaffId(documentRequest.getContributorDetails().getStudentOrtStaffId())
                            .build();
                    Resource document = Resource.builder()
                            .title(documentRequest.getTitle())
                            .description(documentRequest.getDescription())
                            .keywords(documentRequest.getKeywords())
                            .status(ResourceStatus.AWAITING_APPROVAL)
                            .contributorDetails(contributorDetails)
                            .userAccount(userAccount)
                            .resourceCategory(null)
                            .uri(documentRequest.getUri())
                            .build();
                        documents.add(document);

                        String subject = "Submission Received: Your Academic Resource is Awaiting Approval";
                        notificationService.sendNotification(Message.CONTRIBUTION_SUBMITTED_MESSAGE
                                .replace("{resourceTitle}", documentRequest.getTitle()),
                                subject, documentRequest.getContributorDetails().getName(),
                                userAccount, false, false, false);
                });
        List<Resource> savedResources = resourceRepository.saveAllAndFlush(documents);
        if (!savedResources.isEmpty()) {
            notifyAdmin(savedResources.size());
        }
        return savedResources;
    }

    @Async
    public void notifyAdmin(Integer count) {
        log.info("Total number of documents created: {}", count);

        List<UserAccount> userAccounts = userAccountRepository
                .findAllByUserGroupOrderByCreatedOnDesc(UserGroup.ADMIN,Pageable.unpaged()).getContent();
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
    public List<Resource> searchForResources(String searchParam, ResourceCategory category, Pageable pageable) {
        if(!resourceRepository.findAllByTitleContainingIgnoreCase(searchParam).isEmpty()) {
            return resourceRepository.findAllByTitleContainingIgnoreCase(searchParam);
        }
        if(!resourceRepository.findAllByDescriptionContainingIgnoreCase(searchParam).isEmpty()) {
            return resourceRepository.findAllByDescriptionContainingIgnoreCase(searchParam);
        }
        if(!resourceRepository.findAllByKeywordsContainingIgnoreCase(searchParam).isEmpty()) {
            return resourceRepository.findAllByKeywordsContainingIgnoreCase(searchParam);
        }
        if(!resourceRepository.findAllByContributorDetails_NameContainingIgnoreCaseOrderByCreatedOnDesc(searchParam).isEmpty()) {
            return resourceRepository.findAllByResourceCategory(category);
        }
        return new ArrayList<>();
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
        resourceRepository.delete(document);
    }

    @Override
    public List<Resource> getAllResources(String contributorName, String title, String description, String keywords, ResourceCategory category, Pageable pageable) {
        if(Objects.nonNull(title)) {
            return resourceRepository.findAllByTitleContainingIgnoreCase(title);
        }
        if(Objects.nonNull(description)) {
            return resourceRepository.findAllByDescriptionContainingIgnoreCase(description);
        }
        if(Objects.nonNull(keywords)) {
            return resourceRepository.findAllByKeywordsContainingIgnoreCase(keywords);
        }
        if(Objects.nonNull(category)) {
            return resourceRepository.findAllByResourceCategory(category);
        }
        if(Objects.nonNull(contributorName)) {
            return resourceRepository.findAllByContributorDetails_NameContainingIgnoreCaseOrderByCreatedOnDesc(contributorName);
        }
        return resourceRepository.findAll();
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
                resource.getUserAccount(), false, false, false);

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
                    userAccount, false, false, false);
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
                 resource.getUserAccount(), false, false, false);
         return resource;
    }

    @Override
    public List<Resource> getProfileResources(String username, ResourceCategory category) {
        if(Objects.nonNull(category)) {
            return resourceRepository.findAllByUserAccount_UsernameAndResourceCategoryOrderByCreatedOnDesc(username,category);
        }
        return resourceRepository.findAllByUserAccount_UsernameOrderByCreatedOnDesc(username);
    }
}
