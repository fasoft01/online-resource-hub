package zw.co.fasoft.fileupload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * @author Fasoft
 * @date 30/May/2024
 */
@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
@Tag(name = "Files & Resources", description = "files and documents")
public class FileController {

    private final FileService fileService;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Upload a file")
    public ResponseEntity<File> uploadFile(@RequestPart(value = "file") MultipartFile file) throws FileUploadException {
        return fileService.saveFile(file);
    }
}