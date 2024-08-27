package zw.co.fasoft;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import zw.co.fasoft.requests.NotificationRequest;
import zw.co.fasoft.requests.Recipient;
import zw.co.fasoft.requests.UserAccountRequest;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.useraccount.UserAccountRepository;
import zw.co.fasoft.utils.Message;
import zw.co.fasoft.utils.Response;
import zw.co.fasoft.utils.Unique_Credentials_Generation;
import zw.co.fasoft.utils.enums.Status;

@Component
@RequiredArgsConstructor
public class CommonsService {
    private final UserAccountRepository userAccountRepository;
    private final RestTemplate restTemplate;

    @Value("${notifications.service-url}")
    private String notificationsUrl;
    @Value("${api-urls.frontend-login-link}")
    private String loginLink;

    public UserAccount saveUser(UserAccountRequest userAccountRequest){
        var userAccount = UserAccount.builder()
                .fullName(userAccountRequest.getFullName())
                .username(userAccountRequest.getUsername())
                .email(userAccountRequest.getEmail())
                .phoneNumber(userAccountRequest.getPhoneNumber())
                .status(Status.ACTIVATED)
                .userGroup(userAccountRequest.getRole())
                .build();
        return userAccountRepository.save(userAccount);
    }


    @Async
    public void sendNotification(UserAccount userAccount, String fullName){
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .content(Message.USER_ACCOUNT_CREATION_MESSAGE
                        .replace("{username}", userAccount.getUsername())
                        .replace("{password}", Unique_Credentials_Generation.generateRandomPassword(7))
                        .replace("{role}", userAccount.getUserGroup().name())
                        .replace("{login-message}",Message.ADMIN_LOGIN_LINK_MESSAGE.
                                replace("{admin-login-link}",loginLink))
                )
                .subject("ONLINE RESOURCE HUB ACCOUNT CREATION")
                .isSMS(true)
                .recipient(buildRecipient(userAccount,fullName))
                .isEmail(true)
                .isPush(false)
                .build();
        restTemplate.postForObject(notificationsUrl,notificationRequest, Response.class);
    }

    private Recipient buildRecipient(UserAccount userAccount, String fullName) {
        return Recipient.builder()
                .name(fullName)
                .phoneNumber(userAccount.getPhoneNumber())
                .email(userAccount.getEmail())
                .build();
    }
}
