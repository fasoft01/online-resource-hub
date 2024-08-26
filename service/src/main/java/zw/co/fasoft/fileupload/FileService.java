package zw.co.fasoft.fileupload;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ResponseEntity<File> saveFile(MultipartFile file) throws FileUploadException;
}
