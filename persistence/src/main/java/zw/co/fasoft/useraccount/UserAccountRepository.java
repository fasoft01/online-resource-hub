package zw.co.fasoft.useraccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.utils.enums.Status;
import zw.co.fasoft.utils.enums.UserGroup;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    Optional<UserAccount> findUserAccountByEmail(String email);
    Page<UserAccount> findAllByFullNameContainingIgnoreCaseOrderByCreatedOnDesc(String name, Pageable pageable);
    Page<UserAccount> findAllByStatusOrderByCreatedOnDesc(Status status, Pageable pageable);
    Page<UserAccount> findAllByUsernameContainingIgnoreCase(String username, Pageable pageable);

    Optional<UserAccount> findByUsername(String username);
    Page<UserAccount> findAll(Pageable pageable);
    Page<UserAccount> findAllByUserGroupOrderByCreatedOnDesc(UserGroup userGroup, Pageable pageable);
}
