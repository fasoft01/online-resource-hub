package zw.co.fasoft.fileupload;

import org.springframework.data.domain.Pageable;
import zw.co.fasoft.utils.enums.ResourceCategory;

import java.util.List;

public interface ResourceService {

    List<Resource> create(List<ResourceRequest> request, String username);

    Resource update(Long id, ResourceRequest request);

    List<Resource> searchForResources(String searchParam, ResourceCategory category, Pageable pageable);

    Resource getById(Long id);
    void delete(Long id);

    List<Resource> getAllResources(String contributorName, String title, String description, String keywords, ResourceCategory category, Pageable pageable);

    Resource approve(Long resourceId);

    Resource reject(Long resourceId, String reason);

    List<Resource> getProfileResources(String username, ResourceCategory category);
}