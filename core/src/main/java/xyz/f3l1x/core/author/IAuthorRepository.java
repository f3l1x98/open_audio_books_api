package xyz.f3l1x.core.author;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;
import java.util.UUID;

public interface IAuthorRepository extends IBaseRepository<Author> {
    List<Author> findAll();
    List<Author> findAllByIdIn(List<UUID> ids);
    List<Author> findAllForAudioBook(UUID audioBookId);
}
