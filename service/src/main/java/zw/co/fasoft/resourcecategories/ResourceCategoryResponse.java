package zw.co.fasoft.resourcecategories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ResourceCategoryResponse {
    private Long id;
    private String name;
}
