package xyz.f3l1x.core.genre.command.create;

import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

public class CreateGenreCommandHandler implements ICommandHandler<CreateGenreCommand, Genre> {
    private final IGenreRepository repository;

    public CreateGenreCommandHandler(IGenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Genre handle(CreateGenreCommand command) throws Exception {
        Genre genre = Genre.create(command.name());

        return this.repository.save(genre);
    }
}
