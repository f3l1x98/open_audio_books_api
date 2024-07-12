package xyz.f3l1x.core.genre.command.delete;

import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.genre.exception.GenreNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class DeleteGenreCommandHandler implements ICommandHandler<DeleteGenreCommand, Genre> {
    private final IGenreRepository genreRepository;

    public DeleteGenreCommandHandler(IGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre handle(DeleteGenreCommand command) throws Exception {
        Optional<Genre> genreOptional = genreRepository.findById(command.genreId());
        if (genreOptional.isEmpty()) {
            throw new GenreNotFoundException(command.genreId());
        }

        genreRepository.delete(genreOptional.get());

        return genreOptional.get();
    }
}
