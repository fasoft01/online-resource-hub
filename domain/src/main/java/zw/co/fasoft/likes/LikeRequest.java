package zw.co.fasoft.likes;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.fasoft.utils.enums.LikeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequest {
    private Long resourceId;
    @Enumerated(EnumType.STRING)
    private LikeType likeType;
}