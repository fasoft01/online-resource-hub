package zw.co.fasoft.administration;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import zw.co.fasoft.CommonsService;
import zw.co.fasoft.NotificationServiceImpl;
import zw.co.fasoft.auth.KeycloakCommons;
import zw.co.fasoft.auth.LoginResponse;
import zw.co.fasoft.exceptions.IncorrectUsernameOrPasswordException;
import zw.co.fasoft.requests.UserAccountRequest;
import zw.co.fasoft.responses.UserAccountResponse;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.useraccount.UserAccountRepository;
import zw.co.fasoft.utils.Message;
import zw.co.fasoft.utils.enums.Status;
import zw.co.fasoft.utils.enums.UserGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService{
    private final UserAccountRepository userAccountRepository;
    private final CommonsService commonsService;
    private final KeycloakCommons keycloakCommons;
    private final NotificationServiceImpl notificationService;
    private final RestTemplate restTemplate;

    @Value("${api-urls.auth-service}")
    private String authServiceUrl;
    @Override
    public UserAccount createUser(UserAccountRequest userAccountRequest) {
        var userAccount = commonsService.saveUser(userAccountRequest);

        PostUserAccountRequest postUserAccountRequest = PostUserAccountRequest.builder()
                .fullName(userAccount.getFullName())
                .email(userAccount.getEmail())
                .phoneNumber(userAccount.getPhoneNumber())
                .role(userAccount.getUserGroup())
                .build();
        saveKeycloakUser(postUserAccountRequest);
        commonsService.sendNotification(userAccount, userAccount.getFullName());
        return userAccount;
    }

    private void saveKeycloakUser(PostUserAccountRequest postUserAccountRequest) {
        String url = authServiceUrl+"/v1/auth";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PostUserAccountRequest> request = new HttpEntity<>(postUserAccountRequest, headers);

        ResponseEntity<PostUserAccountRequest> response = restTemplate.postForEntity(url, request, PostUserAccountRequest.class);
    }


    @Override
    public UserAccount updateUserProfile(String username,UpdateUserAccountRequest userAccountRequest) {
        var userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userAccount.setEmail(userAccountRequest.getEmail());
        userAccount.setPhoneNumber(userAccountRequest.getPhoneNumber());

        if(!userAccount.getStatus().equals(userAccountRequest.getStatus())){
            if(userAccountRequest.getStatus().equals(Status.ACTIVATED)){
                keycloakCommons.activateUser(userAccount.getUsername(),true);
                userAccount.setStatus(Status.ACTIVATED);
               sendActivationDeactivationNotification(username,"ACTIVATION");
            }
            else{
                deactivateUser(userAccount);
                userAccount.setStatus(Status.DEACTIVATED);
            }
        }
        return userAccountRepository.save(userAccount);
    }
    @Async
    public void sendActivationDeactivationNotification(String userName,String action) {
        // sending notification
        String content = action.equals("ACTIVATION") ? (Message.ACCOUNT_ACTIVATION_MESSAGE) : (Message.ACCOUNT_DEACTIVATION_MESSAGE);
        String subject = "ONLINE RESOURCE HUB ACCOUNT "+action;

        var userAccount = userAccountRepository.findByUsername(userName);

        String recepientName = userAccount.get().getFullName();
        notificationService.sendNotification(content, subject, recepientName, userAccount.get(), true, false, false);
        }

    private void deactivateUser(UserAccount userAccount) {
        keycloakCommons.activateUser(userAccount.getUsername(),false);
        sendActivationDeactivationNotification(userAccount.getUsername(),"DEACTIVATION");
    }

    @Override
    public void deleteUser(Long id) {
        var userAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
         userAccountRepository.delete(userAccount);
    }

    @Override
    public Page<UserAccountResponse> getAllUsers(String name, Status status, UserGroup role, Pageable pageable) {
        if(Objects.nonNull(name)){
            return userAccountRepository
                    .findAllByFullNameContainingIgnoreCaseOrderByCreatedOnDesc(name,pageable)
                    .map(this::createDTO);
        }
        if(Objects.nonNull(role)){
            return userAccountRepository
                    .findAllByUserGroupOrderByCreatedOnDesc(role,pageable)
                    .map(this::createDTO);
        }

        if(Objects.nonNull(status)) {
            return userAccountRepository
                    .findAllByStatusOrderByCreatedOnDesc(status, pageable)
                    .map(this::createDTO);
        }

        return userAccountRepository
                .findAll(pageable)
                .map(this::createDTO);
    }

    private UserAccountResponse createDTO(UserAccount userAccount) {
        return UserAccountResponse.builder()
                .fullName(userAccount.getFullName())
                .username(userAccount.getUsername())
                .email(userAccount.getEmail())
                .phoneNumber(userAccount.getPhoneNumber())
                .status(userAccount.getStatus())
                .role(userAccount.getUserGroup())
                .build();
    }
}
@Builder
@Data
  class PostUserAccountRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserGroup role;
}
