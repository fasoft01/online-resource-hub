package zw.co.fasoft.likes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.fasoft.utils.enums.LikeType;

public interface LikeService {
    LikeResponse likeResource(LikeRequest like);

    LikeResponse getResourceLikes(Long resourceId);

    Page<Like> getUserLikes(String name, Pageable pageable);

    Page<Like> getAllLikes(Pageable pageable, LikeType likeType);
}
