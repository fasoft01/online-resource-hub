package zw.co.fasoft.fileupload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResourceService {

    List<Resource> create(List<ResourceRequest> request, String username);

    Resource update(Long id, ResourceRequest request);

    Page<Resource> searchForResources(String searchParam, Pageable pageable);

    Resource getById(Long id);
    void delete(Long id);

    Page<Resource> getAllResources(String contributorName, String title, String description, String keywords, Long categoryId, Pageable pageable);

    Resource approve(Long resourceId);

    Resource reject(Long resourceId, String reason);

    Page<Resource> getProfileResources(String username, Pageable pageable);

    Page<Resource> searchResourcesForStudent(String searchParam, Pageable pageable);

    Page<Resource> getAllResourcesForStudent(String title, String description, String keywords, String contributorName, Long categoryId, Pageable pageable);
}