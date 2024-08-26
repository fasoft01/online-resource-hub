package zw.co.fasoft.requests;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Fasoft
 * @date 2/May/2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationRequest {
    private String content;
    private String subject;
    private String messageLink;
    @Embedded
    private Recipient recipient;
    private boolean isEmail;
    private boolean isSMS;
    private boolean isPush;
}
