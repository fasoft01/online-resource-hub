package zw.co.fasoft.requests;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Fasoft
 * @date 2/May/2024
 */
@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipient {
    private  String name;
    private  String phoneNumber;
    private String email;
    private  String fcmToken;
}