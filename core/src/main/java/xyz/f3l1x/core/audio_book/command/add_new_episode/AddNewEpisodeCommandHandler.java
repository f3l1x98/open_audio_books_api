package xyz.f3l1x.core.audio_book.command.add_new_episode;

import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.exception.UniqueEpisodeViolationException;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class AddNewEpisodeCommandHandler implements ICommandHandler<AddNewEpisodeCommand, Episode> {
    private final IAudioBookRepository audioBookRepository;

    public AddNewEpisodeCommandHandler(IAudioBookRepository audioBookRepository) {
        this.audioBookRepository = audioBookRepository;
    }

    @Override
    public Episode handle(AddNewEpisodeCommand command) throws AudioBookNotFoundException, UniqueEpisodeViolationException {
        Optional<AudioBook> audioBookOptional = audioBookRepository.findById(command.audioBookId());

        if (audioBookOptional.isEmpty()) {
            throw new AudioBookNotFoundException(command.audioBookId());
        }
        AudioBook audioBook = audioBookOptional.get();

        Episode newEpisode = audioBook.addNewEpisode(command.number(), command.title(), command.summary(), command.releaseDate());

        audioBookRepository.save(audioBook);

        return newEpisode;
    }
}
