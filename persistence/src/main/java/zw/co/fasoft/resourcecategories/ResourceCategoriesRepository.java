package zw.co.fasoft.resourcecategories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import zw.co.fasoft.base.BaseRepository;
import zw.co.fasoft.resourcecategory.ResourceCategory;
import zw.co.fasoft.useraccount.UserAccount;

@Repository
public interface ResourceCategoriesRepository extends BaseRepository<ResourceCategory,Long> {
    Page<ResourceCategory> findAllByCategoryNameContainingIgnoreCase(String name, Pageable pageable);
    Page<ResourceCategory> findAllByCategoryNameContainingIgnoreCaseAndUserAccount_Username(String name,String username,Pageable pageable);
    Page<ResourceCategory> findAllByUserAccount_Username(String name,Pageable pageable);
}
