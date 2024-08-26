//package zw.co.fasoft.utils;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import zw.co.afrosoft.NotificationRequest;
//import zw.co.afrosoft.Recipient;
//import zw.co.afrosoft.useraccount.UserAccount;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class NotificationServiceImpl {
//    private final RestTemplate restTemplate;
//    @Value("${notifications.service-url}")
//    private String notificationsUrl;
//
//    private Recipient buildRecipient(UserAccount userAccount, String fullName) {
//        return Recipient.builder()
//                .name(fullName)
//                .phoneNumber(userAccount.getPhoneNumber())
//                .email(userAccount.getEmail())
//                .build();
//    }
//
//    public void sendNotification(String content, String subject, String fullName, UserAccount userAccount, Boolean isEmail, Boolean isSms, Boolean isPush) {
//        {
//
//            NotificationRequest notificationRequest = NotificationRequest.builder()
//                    .recipient(buildRecipient(userAccount, fullName))
//                    .content(content)
//                    .subject(subject)
//                    .isSMS(isSms)
//                    .isEmail(isEmail)
//                    .isPush(isPush)
//                    .build();
//            restTemplate.postForObject(notificationsUrl, notificationRequest, Response.class);
//        }
//    }
//}
