package zw.co.fasoft.resourcecategories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.fasoft.resourcecategory.ResourceCategory;
import zw.co.fasoft.resourcecategory.ResourceCategoryRequest;

import java.util.List;

public interface ResourceCategoryService {
    ResourceCategoryResponse createResourceCategory(ResourceCategoryRequest request);

    ResourceCategoryResponse updateResourceCategory(Long id, ResourceCategoryRequest request);

    void deleteResourceCategory(Long id);

    ResourceCategory getResourceCategoryById(Long id);

    Page<ResourceCategoryResponse> getAllResourceCategories(String name, Pageable pageable);

    List<ResourceCategoryResponse> getPreferredCategories(String name, Pageable pageable, String username);

    void addPreferredResourceCategory(Long resourceId, String name);

}
