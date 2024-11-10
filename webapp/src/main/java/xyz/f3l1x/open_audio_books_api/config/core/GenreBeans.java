package xyz.f3l1x.open_audio_books_api.config.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.genre.command.create.CreateGenreCommand;
import xyz.f3l1x.core.genre.command.create.CreateGenreCommandHandler;
import xyz.f3l1x.core.genre.command.delete.DeleteGenreCommand;
import xyz.f3l1x.core.genre.command.delete.DeleteGenreCommandHandler;
import xyz.f3l1x.core.genre.query.find_all.FindAllGenreQuery;
import xyz.f3l1x.core.genre.query.find_all.FindAllGenreQueryHandler;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;

@Configuration
public class GenreBeans {

    @Bean
    public IQueryHandler<FindAllGenreQuery, List<Genre>> findAllGenreQueryHandler(IGenreRepository genreRepository) {
        return new FindAllGenreQueryHandler(genreRepository);
    }

    @Bean
    public ICommandHandler<CreateGenreCommand, Genre> createGenreCommandHandler(IGenreRepository genreRepository) {
        return new CreateGenreCommandHandler(genreRepository);
    }

    @Bean
    public ICommandHandler<DeleteGenreCommand, Genre> deleteGenreCommandHandler(IGenreRepository genreRepository) {
        return new DeleteGenreCommandHandler(genreRepository);
    }
}
