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
import xyz.f3l1x.core.audio_book.query.find_by_id.FindAudioBookByIdQuery;
import xyz.f3l1x.core.audio_book.query.find_by_id.FindAudioBookByIdQueryHandler;
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
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.episode.IEpisodeStore;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommand;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommandHandler;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommand;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommandHandler;
import xyz.f3l1x.core.episode.command.upload.UploadCommand;
import xyz.f3l1x.core.episode.command.upload.UploadCommandHandler;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQuery;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQueryHandler;
import xyz.f3l1x.core.episode.query.get_file.GetFileQuery;
import xyz.f3l1x.core.episode.query.get_file.GetFileQueryHandler;
import xyz.f3l1x.core.episode.query.get_file.GetFileQueryResult;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.genre.command.create.CreateGenreCommand;
import xyz.f3l1x.core.genre.command.create.CreateGenreCommandHandler;
import xyz.f3l1x.core.genre.command.delete.DeleteGenreCommand;
import xyz.f3l1x.core.genre.command.delete.DeleteGenreCommandHandler;
import xyz.f3l1x.core.genre.query.find_all.FindAllGenreQuery;
import xyz.f3l1x.core.genre.query.find_all.FindAllGenreQueryHandler;
import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.narrator.command.create.CreateNarratorCommand;
import xyz.f3l1x.core.narrator.command.create.CreateNarratorCommandHandler;
import xyz.f3l1x.core.narrator.command.delete.DeleteNarratorCommand;
import xyz.f3l1x.core.narrator.command.delete.DeleteNarratorCommandHandler;
import xyz.f3l1x.core.narrator.command.update.UpdateNarratorCommand;
import xyz.f3l1x.core.narrator.command.update.UpdateNarratorCommandHandler;
import xyz.f3l1x.core.narrator.query.find_all.FindAllNarratorQuery;
import xyz.f3l1x.core.narrator.query.find_all.FindAllNarratorQueryHandler;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.infra.episode.EpisodeContentStore;
import xyz.f3l1x.infra.episode.IEpisodeContentStore;
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
        audioBookDtoTypeMap.addMappings(m -> m.using(new BaseModelCollectionToIdCollectionConverter()).map(AudioBook::getAuthors, AudioBookDto::setAuthorIds));
        audioBookDtoTypeMap.addMappings(m -> m.using(new BaseModelCollectionToIdCollectionConverter()).map(AudioBook::getNarrators, AudioBookDto::setNarratorIds));

        return mapper;
    }
}
