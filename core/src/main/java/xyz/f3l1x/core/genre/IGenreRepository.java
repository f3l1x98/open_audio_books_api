package xyz.f3l1x.core.genre;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;
import java.util.UUID;

public interface IGenreRepository extends IBaseRepository<Genre> {
    List<Genre> findAll();
    List<Genre> findAllByIdIn(List<UUID> ids);
    List<Genre> findAllForAudioBook(UUID audioBookId);
}
