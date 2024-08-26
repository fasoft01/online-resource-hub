package zw.co.fasoft.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.fasoft.base.BaseRepository;

public interface FileRepository extends BaseRepository<File, Long> {

    boolean existsByName(String name);
}
