package zw.co.fasoft.fileupload;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.fasoft.embeddables.ContributorDetails;

import java.util.Set;

/**
 * @author Fasoft
 * @date 30/May/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequest {
    private String title;
    private String description;
    @Embedded
    private ContributorDetails contributorDetails;
    private String keywords;
    private String uri;
    private Set<Long> resourceCategoryIds;
}
