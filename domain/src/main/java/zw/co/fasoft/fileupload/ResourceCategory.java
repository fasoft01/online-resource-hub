package zw.co.fasoft.fileupload;

import jakarta.persistence.*;
import lombok.*;
import zw.co.fasoft.base.BaseEntity;
import zw.co.fasoft.useraccount.UserAccount;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "preferred_resource_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    @ManyToOne
    private UserAccount userAccount;
}
