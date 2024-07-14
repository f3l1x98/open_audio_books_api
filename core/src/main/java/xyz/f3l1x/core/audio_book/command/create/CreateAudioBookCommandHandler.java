package xyz.f3l1x.core.audio_book.command.create;

import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.List;

public class CreateAudioBookCommandHandler implements ICommandHandler<CreateAudioBookCommand, AudioBook> {
    private final IAudioBookRepository repository;
    private final IGenreRepository genreRepository;

    public CreateAudioBookCommandHandler(IAudioBookRepository repository, IGenreRepository genreRepository) {
        this.repository = repository;
        this.genreRepository = genreRepository;
    }

    @Override
    public AudioBook handle(CreateAudioBookCommand command) {
        // TODO this does not care if one or more genres do not exist (simply ignored)
        List<Genre> genres = genreRepository.findAllByIdIn(command.genreIds());

        AudioBook audioBook = AudioBook.create(
                command.title(),
                command.summary(),
                command.releaseDate(),
                command.ongoing(),
                command.rating(),
                genres);

        return this.repository.save(audioBook);
    }
}
