package zw.co.fasoft.likes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.fasoft.exceptions.RecordNotFoundException;
import zw.co.fasoft.fileupload.Resource;
import zw.co.fasoft.fileupload.ResourceRepository;
import zw.co.fasoft.fileupload.ResourceService;
import zw.co.fasoft.useraccount.UserAccountRepository;
import zw.co.fasoft.utils.enums.LikeType;

import java.util.List;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likesRepository;

    private final UserAccountRepository userAccountRepository;
    private final ResourceService resourceService;
    private final ResourceRepository resourceRepository;

    @Override
    public LikeResponse likeResource(LikeRequest likeRequest,String username) {
        log.info("Liking document : {}", likeRequest);
        var userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        var resource = resourceService.getById(likeRequest.getResourceId());

        var like = Like.builder()
                .likeType(likeRequest.getLikeType())
                .user(userAccount)
                .isDeleted(false)
                .resource(resource)
                .build();
        likesRepository.save(like);
        return getLikeResponse(resource);
    }

    private LikeResponse getLikeResponse(Resource resource) {
        Long LikesCount = likesRepository.countAllByResourceAndLikeTypeAndIsDeleted(resource, LikeType.LIKE,false);
        Long DislikesCount = likesRepository.countAllByResourceAndLikeTypeAndIsDeleted(resource,LikeType.DISLIKE,false);

        return LikeResponse.builder()
                .id(resource.getId())
                .resourceTitle(resource.getTitle())
                .totalLikes(LikesCount)
                .totalDislikes(DislikesCount)
                .build();
    }

    @Override
    public LikeResponse getResourceLikes(Long resourceId) {
        var resource = resourceService.getById(resourceId);
        return getLikeResponse(resource);
    }
    @Override
    public Page<LikeResponse> getAllLikes(Pageable pageable) {
        List<Resource> resourceList = resourceRepository.findAllByIsDeleted(false);

        List<LikeResponse> likeResponses = resourceList.stream()
                .map(this::getLikeResponse)
                .toList();
        return new PageImpl<>(likeResponses, pageable, likeResponses.size());
    }

    @Override
    public LikeResponse undoLikeResource(Long resourceId, String name) {
        var resource = resourceService.getById(resourceId);
        var userAccount = userAccountRepository.findByUsername(name)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
//        likesRepository.deleteAllByUserAndResource(userAccount, resource);
        List<Like> likes = likesRepository.findAllByUserAndResourceAndIsDeleted(userAccount, resource, false);
        likes.forEach(like -> {
            like.setIsDeleted(true);
        });
        likesRepository.saveAll(likes);
        return getLikeResponse(resource);
    }
}
