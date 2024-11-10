package xyz.f3l1x.open_audio_books_api.config.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.author.command.create.CreateAuthorCommand;
import xyz.f3l1x.core.author.command.create.CreateAuthorCommandHandler;
import xyz.f3l1x.core.author.command.delete.DeleteAuthorCommand;
import xyz.f3l1x.core.author.command.delete.DeleteAuthorCommandHandler;
import xyz.f3l1x.core.author.command.update.UpdateAuthorCommand;
import xyz.f3l1x.core.author.command.update.UpdateAuthorCommandHandler;
import xyz.f3l1x.core.author.query.find_all.FindAllAuthorQuery;
import xyz.f3l1x.core.author.query.find_all.FindAllAuthorQueryHandler;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;

@Configuration
public class AuthorBeans {

    @Bean
    public IQueryHandler<FindAllAuthorQuery, List<Author>> findAllAuthorQueryHandler(IAuthorRepository authorRepository) {
        return new FindAllAuthorQueryHandler(authorRepository);
    }

    @Bean
    public ICommandHandler<CreateAuthorCommand, Author> createAuthorCommandHandler(IAuthorRepository authorRepository) {
        return new CreateAuthorCommandHandler(authorRepository);
    }

    @Bean
    public ICommandHandler<UpdateAuthorCommand, Author> updateAuthorCommandHandler(IAuthorRepository authorRepository) {
        return new UpdateAuthorCommandHandler(authorRepository);
    }

    @Bean
    public ICommandHandler<DeleteAuthorCommand, Author> deleteAuthorCommandHandler(IAuthorRepository authorRepository) {
        return new DeleteAuthorCommandHandler(authorRepository);
    }
}
