package zw.co.fasoft.likes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.fasoft.utils.enums.LikeType;

public interface LikeService {

    LikeResponse likeResource(LikeRequest likeRequest, String username);

    LikeResponse getResourceLikes(Long resourceId);

    Page<LikeResponse> getAllLikes(Pageable pageable);
}
