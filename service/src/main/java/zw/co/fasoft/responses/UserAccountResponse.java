package zw.co.fasoft.responses;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.fasoft.utils.enums.Status;
import zw.co.fasoft.utils.enums.UserGroup;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountResponse {
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private UserGroup role;
}
