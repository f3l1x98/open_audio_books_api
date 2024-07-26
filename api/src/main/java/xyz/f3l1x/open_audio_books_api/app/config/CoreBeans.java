package xyz.f3l1x.open_audio_books_api.app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommand;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommandHandler;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommand;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommandHandler;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQuery;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQueryHandler;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.genre.command.create.CreateGenreCommand;
import xyz.f3l1x.core.genre.command.create.CreateGenreCommandHandler;
import xyz.f3l1x.core.genre.command.delete.DeleteGenreCommand;
import xyz.f3l1x.core.genre.command.delete.DeleteGenreCommandHandler;
import xyz.f3l1x.core.genre.query.find_all.FindAllGenreQuery;
import xyz.f3l1x.core.genre.query.find_all.FindAllGenreQueryHandler;
import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.AudioBookDto;
import xyz.f3l1x.open_audio_books_api.app.mapper.BaseModelCollectionToIdCollectionConverter;

import java.util.List;

@Configuration
public class CoreBeans {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        TypeMap<AudioBook, AudioBookDto> audioBookDtoTypeMap = mapper.createTypeMap(AudioBook.class, AudioBookDto.class);
        audioBookDtoTypeMap.addMappings(m -> m.using(new BaseModelCollectionToIdCollectionConverter()).map(AudioBook::getEpisodes, AudioBookDto::setEpisodeIds));
        audioBookDtoTypeMap.addMappings(m -> m.using(new BaseModelCollectionToIdCollectionConverter()).map(AudioBook::getGenres, AudioBookDto::setGenreIds));

        return mapper;
    }

    @Bean
    public IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler(IAudioBookRepository audioBookRepository) {
        return new FindAllAudioBookQueryHandler(audioBookRepository);
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
    public IQueryHandler<FindAllGenreQuery, List<Genre>> findAllGenreQueryHandler(IGenreRepository genreRepository) {
        return new FindAllGenreQueryHandler(genreRepository);
    }

    @Bean
    public ICommandHandler<CreateGenreCommand, Genre> createGenreCommandHandler(IGenreRepository genreRepository) {
        return new CreateGenreCommandHandler(genreRepository);
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
    public IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler(IEpisodeRepository episodeRepository) {
        return new FindAllForAudioBookEpisodeQueryHandler(episodeRepository);
    }

    @Bean
    public ICommandHandler<DeleteGenreCommand, Genre> deleteGenreCommandHandler(IGenreRepository genreRepository) {
        return new DeleteGenreCommandHandler(genreRepository);
    }

    @Bean
    public ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler(IEpisodeRepository episodeRepository) {
        return new DeleteEpisodeCommandHandler(episodeRepository);
    }

    @Bean
    public ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler(IEpisodeRepository episodeRepository) {
        return new UpdateEpisodeCommandHandler(episodeRepository);
    }

    @Bean
    public ICommandHandler<AddNewEpisodeCommand, Episode> addNewEpisodeCommandHandler(IAudioBookRepository audioBookRepository) {
        return new AddNewEpisodeCommandHandler(audioBookRepository);
    }

    @Bean
    public ICommandHandler<DeleteAudioBookCommand, AudioBook> deleteAudioBookCommandHandler(IAudioBookRepository audioBookRepository) {
        return new DeleteAudioBookCommandHandler(audioBookRepository);
    }
}
