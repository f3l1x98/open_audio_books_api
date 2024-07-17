package xyz.f3l1x.core.author.command.create;

import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

public class CreateAuthorCommandHandler implements ICommandHandler<CreateAuthorCommand, Author> {
    private final IAuthorRepository repository;

    public CreateAuthorCommandHandler(IAuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author handle(CreateAuthorCommand command) throws Exception {
        Author author = Author.create(command.firstName(), command.middleName(), command.lastName());
        return repository.save(author);
    }
}
