package zw.co.fasoft.likes;

import org.springframework.stereotype.Repository;
import zw.co.fasoft.base.BaseRepository;
import zw.co.fasoft.fileupload.Resource;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.utils.enums.LikeType;

import java.util.List;

@Repository
public interface LikeRepository extends BaseRepository<Like, Long> {
    Boolean existsByResourceAndUserAndLikeTypeAndIsDeleted(Resource resource, UserAccount user, LikeType likeType,Boolean isDeleted);
    Long countAllByResourceAndLikeTypeAndIsDeleted(Resource resource,LikeType likeType,Boolean isDeleted);
    void deleteAllByUserAndResource(UserAccount user, Resource resource);

    List<Like> findAllByUserAndResourceAndIsDeleted(UserAccount userAccount, Resource resource, Boolean b);
}
