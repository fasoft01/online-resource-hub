package zw.co.fasoft.fileupload;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.fasoft.embeddables.ContributorDetails;
import zw.co.fasoft.utils.enums.ResourceCategory;

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
    private ResourceCategory resourceCategory;
    private String uri;
}
