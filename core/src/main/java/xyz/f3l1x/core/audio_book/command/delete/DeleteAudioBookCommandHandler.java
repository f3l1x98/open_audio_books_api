package xyz.f3l1x.core.audio_book.command.delete;

import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class DeleteAudioBookCommandHandler implements ICommandHandler<DeleteAudioBookCommand, AudioBook> {
    private final IAudioBookRepository audioBookRepository;

    public DeleteAudioBookCommandHandler(IAudioBookRepository audioBookRepository) {
        this.audioBookRepository = audioBookRepository;
    }

    @Override
    public AudioBook handle(DeleteAudioBookCommand command) throws Exception {
        Optional<AudioBook> audioBookOptional = audioBookRepository.findById(command.audioBookId());
        if (audioBookOptional.isEmpty()) {
            throw new AudioBookNotFoundException(command.audioBookId());
        }

        audioBookRepository.delete(audioBookOptional.get());

        return audioBookOptional.get();
    }
}
