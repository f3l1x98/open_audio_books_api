package xyz.f3l1x.core.author.command.update;

import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.author.exception.AuthorNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class UpdateAuthorCommandHandler implements ICommandHandler<UpdateAuthorCommand, Author> {
    private final IAuthorRepository repository;

    public UpdateAuthorCommandHandler(IAuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author handle(UpdateAuthorCommand command) throws AuthorNotFoundException {
        Optional<Author> current = repository.findById(command.id());
        if (current.isEmpty()) {
            throw new AuthorNotFoundException(command.id());
        }
        Author author = current.get();
        author.update(command.firstName(), command.middleName(), command.lastName());

        return repository.save(author);
    }
}
