package xyz.f3l1x.core.episode;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;
import java.util.Optional;

public interface IEpisodeRepository extends IBaseRepository<Episode> {
    Optional<Episode> findById(Long id);
    List<Episode> findAllByIdIn(List<Long> ids);
    List<Episode> findAllForAudioBook(Long audioBookId);
    void delete(Episode episode);
}
