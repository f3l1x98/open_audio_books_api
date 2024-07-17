package xyz.f3l1x.core.author.query.find_all;

import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FindAllAuthorQueryHandler implements IQueryHandler<FindAllAuthorQuery, List<Author>> {
    private final IAuthorRepository repository;

    public FindAllAuthorQueryHandler(IAuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Author> handle(FindAllAuthorQuery query) {
        Optional<UUID> audioBookId = query.audioBookId();
        List<Author> authors;
        if (audioBookId.isPresent()) {
            authors = repository.findAllForAudioBook(audioBookId.get());
        } else {
            authors = repository.findAll();
        }
        return authors;
    }
}
