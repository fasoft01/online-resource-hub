package zw.co.fasoft.administration;

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
public class UpdateUserAccountRequest {
    @Enumerated(EnumType.STRING)
    private Status status;
    private String email;
    private String phoneNumber;
}
