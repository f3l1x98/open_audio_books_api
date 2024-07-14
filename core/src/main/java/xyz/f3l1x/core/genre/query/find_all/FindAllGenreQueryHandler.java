package xyz.f3l1x.core.genre.query.find_all;

import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FindAllGenreQueryHandler implements IQueryHandler<FindAllGenreQuery, List<Genre>> {
    private final IGenreRepository repository;

    public FindAllGenreQueryHandler(IGenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Genre> handle(FindAllGenreQuery query) {
        Optional<UUID> audioBookId = query.forAudioBook();
        List<Genre> genres;
        if (audioBookId.isPresent()) {
            genres = repository.findAllForAudioBook(audioBookId.get());
        } else {
            genres = repository.findAll();
        }
        return genres;
    }
}
