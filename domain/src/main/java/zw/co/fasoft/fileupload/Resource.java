package zw.co.fasoft.fileupload;

import jakarta.persistence.*;
import lombok.*;
import zw.co.fasoft.base.BaseEntity;
import zw.co.fasoft.embeddables.ContributorDetails;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.utils.enums.ResourceCategory;
import zw.co.fasoft.utils.enums.ResourceStatus;

/**
 * @author Fasoft
 * @date 30/May/2024
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Resource extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ResourceCategory resourceCategory;
    @Embedded
    private ContributorDetails contributorDetails;
    private String keywords;
    @Enumerated(EnumType.STRING)
    private ResourceStatus status;
    private String uri;
    @ManyToOne
    private UserAccount userAccount;
}