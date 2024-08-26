package zw.co.fasoft.auth;

import lombok.Builder;
import lombok.Data;

/**
 * @author Terrance Nyamfukudza
 * @date 28/Sep/2023
 */
@Data
@Builder
public class RoleDetails {
    private String id;
    private String name;
    private String description;
    private boolean compose;
    private boolean clientRole;
    private String containerId;
}
