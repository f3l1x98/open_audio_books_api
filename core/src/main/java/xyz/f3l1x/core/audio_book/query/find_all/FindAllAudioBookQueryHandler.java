package xyz.f3l1x.core.audio_book.query.find_all;

import lombok.extern.slf4j.Slf4j;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;

@Slf4j
public class FindAllAudioBookQueryHandler implements IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> {

    private final IAudioBookRepository repository;

    public FindAllAudioBookQueryHandler(IAudioBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AudioBook> handle(FindAllAudioBookQuery query) {
        return this.repository.findAll();
    }
}
