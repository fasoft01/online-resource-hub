package zw.co.fasoft.resourcecategories;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import zw.co.fasoft.base.BaseRepository;
import zw.co.fasoft.resourcecategory.ResourceCategory;
import zw.co.fasoft.useraccount.UserAccount;

@Repository
public interface ResourceCategoriesRepository extends BaseRepository<ResourceCategory,Long> {
    Page<ResourceCategory> findAllByCategoryNameContainingIgnoreCaseAndIsDeleted(String name, Boolean isDeleted,Pageable pageable);
    Page<ResourceCategory> findAllByCategoryNameContainingIgnoreCaseAndUserAccount_Username(String name,String username,Pageable pageable);
    Page<ResourceCategory> findAllByUserAccount_Username(String name,Pageable pageable);

    Page<ResourceCategory> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
}
