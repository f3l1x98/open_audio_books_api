package xyz.f3l1x.core.audio_book.query.find_by_id;

import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.Optional;

public class FindAudioBookByIdQueryHandler implements IQueryHandler<FindAudioBookByIdQuery, AudioBook> {
    private final IAudioBookRepository repository;

    public FindAudioBookByIdQueryHandler(IAudioBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public AudioBook handle(FindAudioBookByIdQuery query) throws AudioBookNotFoundException {
        Optional<AudioBook> audioBookOptional = repository.findById(query.id());
        if (audioBookOptional.isEmpty()) {
            throw new AudioBookNotFoundException(query.id());
        }

        return audioBookOptional.get();
    }
}
