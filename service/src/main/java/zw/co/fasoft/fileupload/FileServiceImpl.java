package zw.co.fasoft.fileupload;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private static final String documentFolderUrl = "docs/";
    private final FileRepository fileRepository;

    @Override
    public ResponseEntity<File> saveFile(MultipartFile file) throws FileUploadException {
        String uploadRootPath = new java.io.File(documentFolderUrl).getAbsolutePath();
        java.io.File uploadRootDir = new java.io.File(uploadRootPath);
        File savedFile = new File();
        if (!uploadRootDir.exists())
            uploadRootDir.mkdirs();

        if (Objects.nonNull(file)) {
            try {

                String nm = Objects.requireNonNull(file.getOriginalFilename())
                        .replace(" ", "")
                        .replace("-", "");
                String filename = generateRandomString().concat(nm);
                String tempUrl = documentFolderUrl.concat(filename);
                java.io.File serverFile = new java.io.File(uploadRootDir.getPath() + java.io.File.separator + filename);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
                savedFile = fileRepository.save(File.builder()
                        .extension(getExtensionByStringHandling(nm).get())
                        .location(tempUrl)
                        .name(filename)
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
                throw new FileUploadException("Error uploading file : ");
            }
        }


        return ResponseEntity.ok(savedFile);
    }


    private Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private String generateRandomString() {

        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                + "lmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();

    }
}
