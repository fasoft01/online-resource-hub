package zw.co.fasoft.likes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.fasoft.utils.enums.LikeType;

import java.security.Principal;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Resource Liking", description = "like and dislike resources")
@SecurityRequirement(name = "authorization")
public class LikesController {
    public LikeService likesService;

    @PostMapping
    @Operation(description = "Like or dislike a resource")
    public ResponseEntity<LikeResponse> like(@RequestBody LikeRequest like) {
        return ResponseEntity.ok(likesService.likeResource(like));
    }

    @GetMapping("/{resource-id}")
    @Operation(description = "Get all likes for a resource")
    public ResponseEntity<LikeResponse> getResourceLikes(@PathVariable("resource-id") Long resourceId) {
        return ResponseEntity.ok(likesService.getResourceLikes(resourceId));
    }

    @GetMapping("/profile")
    @Operation(description = "Get all likes")
    public ResponseEntity<Page<Like>> getUserLikes(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            Principal principal) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(likesService.getUserLikes(principal.getName(),pageable));
    }

    @GetMapping
    public ResponseEntity<Page<Like>> getAllLikes(
            @RequestParam(required = false) LikeType likeType,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(likesService.getAllLikes(pageable, likeType));
    }
}
