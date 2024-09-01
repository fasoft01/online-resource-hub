package zw.co.fasoft.likes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeResponse {
    private Long id;
    private String resourceTitle;
    private Long totalLikes;
    private Long totalDislikes;
}