package xyz.f3l1x.core.audio_book.command.add_genre;

import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.genre.exception.GenreNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class AddGenreCommandHandler implements ICommandHandler<AddGenreCommand, AudioBook> {
    private final IGenreRepository genreRepository;
    private final IAudioBookRepository audioBookRepository;

    public AddGenreCommandHandler(IGenreRepository genreRepository, IAudioBookRepository audioBookRepository) {
        this.genreRepository = genreRepository;
        this.audioBookRepository = audioBookRepository;
    }

    @Override
    public AudioBook handle(AddGenreCommand command) throws Exception {
        Optional<AudioBook> audioBookOptional = audioBookRepository.findById(command.audioBookId());
        if (audioBookOptional.isEmpty()) {
            throw new AudioBookNotFoundException(command.audioBookId());
        }

        Optional<Genre> genreOptional = genreRepository.findById(command.genreId());
        if (genreOptional.isEmpty()) {
            throw new GenreNotFoundException(command.genreId());
        }

        AudioBook audioBook = audioBookOptional.get();
        audioBook.addGenre(genreOptional.get());

        return audioBookRepository.save(audioBook);
    }
}
