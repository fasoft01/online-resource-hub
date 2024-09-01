package zw.co.fasoft.fileupload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import zw.co.fasoft.base.BaseRepository;
import zw.co.fasoft.resourcecategory.ResourceCategory;
import zw.co.fasoft.utils.enums.ResourceStatus;

import java.util.List;

@Repository
public interface ResourceRepository extends BaseRepository<Resource, Long> {
    List<Resource> deleteResourceById(Long id);

    Page<Resource> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Resource> findAllByTitleContainingIgnoreCaseAndStatus(String title, ResourceStatus approved, Pageable pageable);
    Page<Resource> findAllByContributorDetails_NameContainingIgnoreCase(String name,Pageable pageable);
    List<Resource> findAllByUserAccount_UsernameAndResourceCategoryOrderByCreatedOnDesc(String username, ResourceCategory resourceCategory);
    Page<Resource> findAllByUserAccount_Username(String username,Pageable pageable);
    Page<Resource> findAllByKeywordsContainingIgnoreCase(String keywords,Pageable pageable);
    Page<Resource> findAllByDescriptionContainingIgnoreCase(String description,Pageable pageable);
    Page<Resource> findAllByDescriptionContainingIgnoreCaseAndStatus(String description, ResourceStatus approved, Pageable pageable);
    Page<Resource> findAllByResourceCategory(ResourceCategory resourceCategory,Pageable pageable);
    boolean existsByTitle(String name);

    Page<Resource> findAllByKeywordsContainingIgnoreCaseAndStatus(String keywords, ResourceStatus approved, Pageable pageable);

    Page<Resource> findAllByResourceCategoryAndStatus(ResourceCategory resourceCategory,ResourceStatus resourceStatus, Pageable pageable);

    Page<Resource> findAllByContributorDetails_NameContainingIgnoreCaseAndStatus( String contributorName,ResourceStatus approved, Pageable pageable);

    Page<Resource> findAllByStatus(ResourceStatus resourceStatus, Pageable pageable);
}