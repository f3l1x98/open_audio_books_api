package xyz.f3l1x.infra.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJpaGenreRepository extends JpaRepository<GenreEntity, Long> {
    List<GenreEntity> findAllByIdIn(List<Long> ids);
}
