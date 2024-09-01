package zw.co.fasoft.likes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.fasoft.utils.enums.LikeType;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likesRepository;

    @Override
    public LikeResponse likeResource(LikeRequest like) {
        return null;
    }

    @Override
    public LikeResponse getResourceLikes(Long resourceId) {
        return null;
    }

    @Override
    public Page<Like> getUserLikes(String name, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Like> getAllLikes(Pageable pageable, LikeType likeType) {
        return null;
    }
}
