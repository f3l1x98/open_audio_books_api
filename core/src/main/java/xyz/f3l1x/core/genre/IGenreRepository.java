package xyz.f3l1x.core.genre;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;
import java.util.Optional;

public interface IGenreRepository extends IBaseRepository<Genre> {
    Optional<Genre> findById(Long id);
    List<Genre> findAll();
    List<Genre> findAllByIdIn(List<Long> ids);
    List<Genre> findAllForAudioBook(Long audioBookId);
}
