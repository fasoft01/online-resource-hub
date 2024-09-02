package zw.co.fasoft.administration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.fasoft.requests.UserAccountRequest;
import zw.co.fasoft.responses.UserAccountResponse;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.utils.enums.Status;
import zw.co.fasoft.utils.enums.UserGroup;

public interface AdministrationService {
    UserAccount createUser(UserAccountRequest userAccountRequest);
    UserAccount updateUserProfile(String name,UpdateUserAccountRequest userAccountRequest);
    void deleteUser(Long id);
    Page<UserAccountResponse> getAllUsers(String name, Status status, UserGroup role, Pageable pageable);

    UserAccount getUserProfile(String name);
}
