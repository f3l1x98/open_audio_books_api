package xyz.f3l1x.core.audio_book.command.create;

import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.exception.AuthorRequiredException;
import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.List;

public class CreateAudioBookCommandHandler implements ICommandHandler<CreateAudioBookCommand, AudioBook> {
    private final IAudioBookRepository repository;
    private final IGenreRepository genreRepository;
    private final IAuthorRepository authorRepository;

    public CreateAudioBookCommandHandler(IAudioBookRepository repository, IGenreRepository genreRepository, IAuthorRepository authorRepository) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public AudioBook handle(CreateAudioBookCommand command) throws AuthorRequiredException {
        // TODO this does not care if one or more genres do not exist (simply ignored)
        List<Genre> genres = genreRepository.findAllByIdIn(command.genreIds());
        List<Author> authors = authorRepository.findAllByIdIn(command.authorIds());

        AudioBook audioBook = AudioBook.create(
                command.title(),
                command.summary(),
                command.releaseDate(),
                command.ongoing(),
                command.rating(),
                genres,
                authors);

        return this.repository.save(audioBook);
    }
}
