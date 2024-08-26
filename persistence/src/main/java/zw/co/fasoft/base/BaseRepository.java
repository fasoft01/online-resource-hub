package zw.co.fasoft.base;

import io.micrometer.common.lang.NonNullApi;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@NonNullApi
@Transactional
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    // T represents the entity type that te repository will manage
    Optional<T> findById(ID id);
    List<T> findAll();
    <S extends T> S save(S entity);
    void delete(T entity);
}
