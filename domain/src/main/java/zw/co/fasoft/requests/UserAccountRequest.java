package zw.co.fasoft.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.fasoft.utils.enums.Status;
import zw.co.fasoft.utils.enums.UserGroup;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountRequest {
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserGroup role;
}
