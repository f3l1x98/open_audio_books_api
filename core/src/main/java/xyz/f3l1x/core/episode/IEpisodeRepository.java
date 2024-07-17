package xyz.f3l1x.core.episode;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;
import java.util.UUID;

public interface IEpisodeRepository extends IBaseRepository<Episode> {
    List<Episode> findAllByIdIn(List<UUID> ids);
    List<Episode> findAllForAudioBook(UUID audioBookId);
}
