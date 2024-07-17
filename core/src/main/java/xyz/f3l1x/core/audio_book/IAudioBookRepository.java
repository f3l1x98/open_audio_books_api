package xyz.f3l1x.core.audio_book;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;

public interface IAudioBookRepository extends IBaseRepository<AudioBook> {
    List<AudioBook> findAll();
}
