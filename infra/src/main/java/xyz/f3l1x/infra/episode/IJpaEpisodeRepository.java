package xyz.f3l1x.infra.episode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJpaEpisodeRepository extends JpaRepository<EpisodeEntity, Long> {
    List<EpisodeEntity> findAllByIdIn(List<Long> ids);
}
