package xyz.f3l1x.core.episode;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;

public interface IEpisodeRepository extends IBaseRepository<Episode> {
    List<Episode> findAllByIdIn(List<Long> ids);
    List<Episode> findAllForAudioBook(Long audioBookId);
}
