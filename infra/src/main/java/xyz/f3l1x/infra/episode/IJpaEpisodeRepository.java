package xyz.f3l1x.infra.episode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IJpaEpisodeRepository extends JpaRepository<EpisodeEntity, UUID> {
    List<EpisodeEntity> findAllByIdIn(List<UUID> ids);
    List<EpisodeEntity> findAllByAudioBook_Id(UUID audioBookId);
}
