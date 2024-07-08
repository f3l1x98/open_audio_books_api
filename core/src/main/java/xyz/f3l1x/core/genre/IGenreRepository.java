package xyz.f3l1x.core.genre;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;

public interface IGenreRepository extends IBaseRepository<Genre> {
    List<Genre> findAllByIdIn(List<Long> ids);
}
