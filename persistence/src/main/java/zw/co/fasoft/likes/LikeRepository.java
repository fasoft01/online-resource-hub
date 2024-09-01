package zw.co.fasoft.likes;

import org.springframework.stereotype.Repository;
import zw.co.fasoft.base.BaseRepository;
import zw.co.fasoft.fileupload.Resource;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.utils.enums.LikeType;

@Repository
public interface LikeRepository extends BaseRepository<Like, Long> {
    Boolean existsByResourceAndUserAndLikeType(Resource resource, UserAccount user, LikeType likeType);
    Long countAllByResourceAndLikeType(Resource resource, LikeType likeType);
    void deleteAllByUserAndResource(UserAccount user, Resource resource);
}
