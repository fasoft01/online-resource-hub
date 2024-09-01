package zw.co.fasoft.resourcecategory;

import jakarta.persistence.*;
import lombok.*;
import zw.co.fasoft.base.BaseEntity;
import zw.co.fasoft.fileupload.Resource;
import zw.co.fasoft.useraccount.UserAccount;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "resource_categories")
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
    @ManyToMany
    private List<Resource> resources = new ArrayList<>();
}
