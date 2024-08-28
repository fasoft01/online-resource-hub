package zw.co.fasoft.useraccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import zw.co.fasoft.base.BaseEntity;
import zw.co.fasoft.fileupload.Resource;
import zw.co.fasoft.fileupload.ResourceCategory;
import zw.co.fasoft.utils.enums.Status;
import zw.co.fasoft.utils.enums.UserGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fasoft
 * @date 2/May/2024
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private UserGroup userGroup;
    @ManyToMany
    @JsonIgnore
    private List<ResourceCategory> preferredCategories = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resource> resources = new ArrayList<>();
}