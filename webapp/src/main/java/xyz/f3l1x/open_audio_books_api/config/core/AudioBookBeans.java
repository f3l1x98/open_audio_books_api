package xyz.f3l1x.open_audio_books_api.config.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.command.add_genre.AddGenreCommand;
import xyz.f3l1x.core.audio_book.command.add_genre.AddGenreCommandHandler;
import xyz.f3l1x.core.audio_book.command.add_new_episode.AddNewEpisodeCommand;
import xyz.f3l1x.core.audio_book.command.add_new_episode.AddNewEpisodeCommandHandler;
import xyz.f3l1x.core.audio_book.command.create.CreateAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.create.CreateAudioBookCommandHandler;
import xyz.f3l1x.core.audio_book.command.delete.DeleteAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.delete.DeleteAudioBookCommandHandler;
import xyz.f3l1x.core.audio_book.command.remove_genre.RemoveGenreCommand;
import xyz.f3l1x.core.audio_book.command.remove_genre.RemoveGenreCommandHandler;
import xyz.f3l1x.core.audio_book.command.update.UpdateAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.update.UpdateAudioBookCommandHandler;
import xyz.f3l1x.core.audio_book.query.find_all.FindAllAudioBookQuery;
import xyz.f3l1x.core.audio_book.query.find_all.FindAllAudioBookQueryHandler;
import xyz.f3l1x.core.audio_book.query.find_by_id.FindAudioBookByIdQuery;
import xyz.f3l1x.core.audio_book.query.find_by_id.FindAudioBookByIdQueryHandler;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;

@Configuration
public class AudioBookBeans {

    @Bean
    public IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler(IAudioBookRepository audioBookRepository) {
        return new FindAllAudioBookQueryHandler(audioBookRepository);
    }

    @Bean
    public IQueryHandler<FindAudioBookByIdQuery, AudioBook> findAudioBookByIdQueryHandler(IAudioBookRepository audioBookRepository) {
        return new FindAudioBookByIdQueryHandler(audioBookRepository);
    }

    @Bean
    public ICommandHandler<CreateAudioBookCommand, AudioBook> createAudioBookCommandHandler(IAudioBookRepository audioBookRepository, IGenreRepository genreRepository, IAuthorRepository authorRepository, INarratorRepository narratorRepository) {
        return new CreateAudioBookCommandHandler(audioBookRepository, genreRepository, authorRepository, narratorRepository);
    }

    @Bean
    public ICommandHandler<UpdateAudioBookCommand, AudioBook> updateAudioBookCommandHandler(IAudioBookRepository audioBookRepository) {
        return new UpdateAudioBookCommandHandler(audioBookRepository);
    }

    @Bean
    public ICommandHandler<DeleteAudioBookCommand, AudioBook> deleteAudioBookCommandHandler(IAudioBookRepository audioBookRepository) {
        return new DeleteAudioBookCommandHandler(audioBookRepository);
    }

    @Bean
    public ICommandHandler<AddGenreCommand, AudioBook> addGenreCommandHandler(IAudioBookRepository audioBookRepository, IGenreRepository genreRepository) {
        return new AddGenreCommandHandler(genreRepository, audioBookRepository);
    }

    @Bean
    public ICommandHandler<RemoveGenreCommand, AudioBook> removeGenreCommandHandler(IAudioBookRepository audioBookRepository, IGenreRepository genreRepository) {
        return new RemoveGenreCommandHandler(genreRepository, audioBookRepository);
    }

    @Bean
    public ICommandHandler<AddNewEpisodeCommand, Episode> addNewEpisodeCommandHandler(IAudioBookRepository audioBookRepository) {
        return new AddNewEpisodeCommandHandler(audioBookRepository);
    }
}
