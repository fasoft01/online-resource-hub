package zw.co.fasoft.likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponse {
    private String resourceName;
    private Long totalLikes;
    private Long totalDislikes;
}