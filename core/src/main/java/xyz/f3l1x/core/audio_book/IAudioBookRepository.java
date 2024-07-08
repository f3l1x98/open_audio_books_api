package xyz.f3l1x.core.audio_book;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;
import java.util.Optional;

public interface IAudioBookRepository extends IBaseRepository<AudioBook> {
    List<AudioBook> findAll();

    Optional<AudioBook> findById(Long id);
}
