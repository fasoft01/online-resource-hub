package zw.co.fasoft.fileupload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Fasoft
 * @date 30/May/2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FileRequest {
        private String name;
        private String extension;
        private String location;
    }
