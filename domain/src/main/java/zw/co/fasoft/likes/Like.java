package zw.co.fasoft.likes;

import jakarta.persistence.*;
import lombok.*;
import zw.co.fasoft.fileupload.Resource;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.utils.enums.LikeType;

@Entity
@Table(name = "likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LikeType likeType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

}
