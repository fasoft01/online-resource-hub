package zw.co.fasoft.fileupload;

import org.springframework.stereotype.Repository;
import zw.co.fasoft.base.BaseRepository;

import java.util.List;

@Repository
public interface ResourceRepository extends BaseRepository<Resource, Long> {
    List<Resource> deleteResourceById(Long id);

    List<Resource> findAllByTitleContainingIgnoreCase(String title);
    List<Resource> findAllByContributorDetails_NameContainingIgnoreCaseOrderByCreatedOnDesc(String name);
    List<Resource> findAllByUserAccount_UsernameAndResourceCategoryOrderByCreatedOnDesc(String username, ResourceCategory resourceCategory);
    List<Resource> findAllByUserAccount_UsernameOrderByCreatedOnDesc(String username);
    List<Resource> findAllByKeywordsContainingIgnoreCase(String keywords);
    List<Resource> findAllByDescriptionContainingIgnoreCase(String description);
    List<Resource> findAllByResourceCategory(ResourceCategory resourceCategory);
    boolean existsByTitle(String name);
}