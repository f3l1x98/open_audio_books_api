package xyz.f3l1x.infra.episode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJpaEpisodeRepository extends JpaRepository<EpisodeEntity, Long> {
    List<EpisodeEntity> findAllByIdIn(List<Long> ids);
}
