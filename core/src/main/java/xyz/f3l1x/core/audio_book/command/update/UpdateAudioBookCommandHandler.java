package xyz.f3l1x.core.audio_book.command.update;

import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class UpdateAudioBookCommandHandler implements ICommandHandler<UpdateAudioBookCommand, AudioBook>  {
    private final IAudioBookRepository repository;

    public UpdateAudioBookCommandHandler(IAudioBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public AudioBook handle(UpdateAudioBookCommand command) throws AudioBookNotFoundException {
        Optional<AudioBook> current = repository.findById(command.id());
        if (current.isEmpty()) {
            throw new AudioBookNotFoundException(command.id());
        }
        AudioBook book = current.get();
        book.update(command.title(), command.summary(), command.releaseDate(), command.ongoing(), command.rating());

        return repository.save(book);
    }
}
