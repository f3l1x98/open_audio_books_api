package xyz.f3l1x.core.author.command.delete;

import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.author.exception.AuthorNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class DeleteAuthorCommandHandler implements ICommandHandler<DeleteAuthorCommand, Author> {
    private final IAuthorRepository repository;

    public DeleteAuthorCommandHandler(IAuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author handle(DeleteAuthorCommand command) throws AuthorNotFoundException {
        Optional<Author> authorOptional = repository.findById(command.authorId());
        if (authorOptional.isEmpty()) {
            throw new AuthorNotFoundException(command.authorId());
        }
        Author author = authorOptional.get();

        repository.delete(author);
        return author;
    }
}
