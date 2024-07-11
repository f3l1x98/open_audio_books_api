package xyz.f3l1x.infra.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJpaGenreRepository extends JpaRepository<GenreEntity, Long> {
    List<GenreEntity> findAllByIdIn(List<Long> ids);
    List<GenreEntity> findAllByAudioBooks_Id(Long audioBooksId);
}
