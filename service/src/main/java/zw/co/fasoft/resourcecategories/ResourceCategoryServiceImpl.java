package zw.co.fasoft.resourcecategories;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.fasoft.exceptions.RecordNotFoundException;
import zw.co.fasoft.fileupload.Resource;
import zw.co.fasoft.fileupload.ResourceRepository;
import zw.co.fasoft.resourcecategory.ResourceCategory;
import zw.co.fasoft.resourcecategory.ResourceCategoryRequest;
import zw.co.fasoft.useraccount.UserAccountRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService{

    private final ResourceCategoriesRepository resourceCategoryRepository;
    private final UserAccountRepository userAccountRepository;
    private final ResourceRepository resourceRepository;
    @Override
    public ResourceCategoryResponse createResourceCategory(ResourceCategoryRequest request) {
        var resourceCategory = ResourceCategory.builder()
                .categoryName(request.getCategoryName())
                .isDeleted(false)
                .build();
        return getResourceCategoryResponse(resourceCategoryRepository.save(resourceCategory));
    }

    private ResourceCategoryResponse getResourceCategoryResponse(ResourceCategory resourceCategory) {
        return ResourceCategoryResponse.builder()
                .name(resourceCategory.getCategoryName())
                .id(resourceCategory.getId())
                .build();
    }

    @Override
    public ResourceCategoryResponse updateResourceCategory(Long id, ResourceCategoryRequest request) {
        var resourceCategory = getResourceCategoryById(id);
        resourceCategory.setCategoryName(request.getCategoryName());
        return getResourceCategoryResponse(resourceCategoryRepository.save(resourceCategory));
    }

    @Transactional
    @Override
    public void deleteResourceCategory(Long categoryId) {
        // Find the category
        ResourceCategory category = resourceCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("Resource Category not found"));

        // Set the isDeleted flag to true
        category.setIsDeleted(true);

        resourceCategoryRepository.save(category);
    }


    @Override
    public ResourceCategory getResourceCategoryById(Long id) {
        return resourceCategoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Resource Category not found"));
    }

    @Override
    public ResourceCategory byId(Long id) {
        return resourceCategoryRepository.findById(id)
                .orElse(null);
    }
    @Override
    public Page<ResourceCategoryResponse> getAllResourceCategories(String name, Pageable pageable) {
        if(Objects.nonNull(name)){
            return resourceCategoryRepository.findAllByCategoryNameContainingIgnoreCaseAndIsDeleted(name,false, pageable).map(this::getResourceCategoryResponse);
        }
        return resourceCategoryRepository.findAllByIsDeleted(false,pageable).map(this::getResourceCategoryResponse);
    }


    @Override
    public List<ResourceCategoryResponse> getPreferredCategories(String name, Pageable pageable, String username) {
        var userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

            return userAccountRepository.findByUsername(username).get().getPreferredCategories().stream().map(this::getResourceCategoryResponse).collect(Collectors.toList());
    }
    @Override
    public void addPreferredResourceCategory(Long resourceId, String name) {
        var resourceCategory = getResourceCategoryById(resourceId);
        var userAccount = userAccountRepository.findByUsername(name);

        if(!userAccount.get().getPreferredCategories().contains(resourceCategory)){
            userAccount.get().getPreferredCategories().add(resourceCategory);
            userAccountRepository.save(userAccount.get());
        }
    }
}
