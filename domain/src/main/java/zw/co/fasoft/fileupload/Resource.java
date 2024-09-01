package zw.co.fasoft.fileupload;

import jakarta.persistence.*;
import lombok.*;
import zw.co.fasoft.base.BaseEntity;
import zw.co.fasoft.embeddables.ContributorDetails;
import zw.co.fasoft.likes.Like;
import zw.co.fasoft.resourcecategory.ResourceCategory;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.utils.enums.ResourceStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Fasoft
 * @date 30/May/2024
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "resources")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Embedded
    private ContributorDetails contributorDetails;
    private String keywords;
    @Enumerated(EnumType.STRING)
    private ResourceStatus status;
    private String uri;
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();
    @ManyToMany
    private List<ResourceCategory> resourceCategory = new ArrayList<>();
}
