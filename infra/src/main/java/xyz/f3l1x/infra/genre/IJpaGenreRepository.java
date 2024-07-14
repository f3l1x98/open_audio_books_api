package xyz.f3l1x.infra.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IJpaGenreRepository extends JpaRepository<GenreEntity, UUID> {
    List<GenreEntity> findAllByIdIn(List<UUID> ids);
    List<GenreEntity> findAllByAudioBooks_Id(UUID audioBooksId);
}
