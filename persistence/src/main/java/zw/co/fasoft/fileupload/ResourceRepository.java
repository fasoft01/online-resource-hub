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

    Page<Resource> findAllByTitleContainingIgnoreCaseAndIsDeleted(String title,Boolean isDeleted, Pageable pageable);
    Page<Resource> findAllByTitleContainingIgnoreCaseAndStatusAndIsDeleted(String title, ResourceStatus approved,Boolean isDeleted ,Pageable pageable);
    Page<Resource> findAllByContributorDetails_NameContainingIgnoreCaseAndIsDeleted(String name,Boolean isDeleted,Pageable pageable);
    List<Resource> findAllByUserAccount_UsernameAndResourceCategoryOrderByCreatedOnDesc(String username, ResourceCategory resourceCategory);
    Page<Resource> findAllByUserAccount_UsernameAndIsDeleted(String username,Boolean isDeleted,Pageable pageable);
    Page<Resource> findAllByKeywordsContainingIgnoreCaseAndIsDeleted(String keywords,Boolean isDeleted,Pageable pageable);
    Page<Resource> findAllByDescriptionContainingIgnoreCaseAndIsDeleted(String description,Boolean isDeleted,Pageable pageable);
    Page<Resource> findAllByDescriptionContainingIgnoreCaseAndStatusAndIsDeleted(String description, ResourceStatus approved, Boolean isDeleted, Pageable pageable);
    Page<Resource> findAllByResourceCategory(ResourceCategory resourceCategory,Pageable pageable);
    boolean existsByTitle(String name);

    Page<Resource> findAllByKeywordsContainingIgnoreCaseAndStatusAndIsDeleted(String keywords, ResourceStatus approved,Boolean isDeleted, Pageable pageable);

    Page<Resource> findAllByResourceCategoryAndStatusAndIsDeleted(ResourceCategory resourceCategory,ResourceStatus resourceStatus, Boolean isDeleted, Pageable pageable);

    Page<Resource> findAllByContributorDetails_NameContainingIgnoreCaseAndStatusAndIsDeleted( String contributorName,ResourceStatus approved,Boolean isDeleted, Pageable pageable);

    Page<Resource> findAllByStatusAndIsDeleted(ResourceStatus resourceStatus,Boolean isDeleted, Pageable pageable);

    Page<Resource> findAllByResourceCategoryAndIsDeleted(ResourceCategory resourceCategory,Boolean isDeleted, Pageable pageable);

    Page<Resource> findAllByIsDeleted(Boolean isDeleted,Pageable pageable);
    List<Resource> findAllByIsDeleted(Boolean isDeleted);
}