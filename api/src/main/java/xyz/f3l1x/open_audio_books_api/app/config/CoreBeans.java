package xyz.f3l1x.open_audio_books_api.app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.command.create.CreateAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.create.CreateAudioBookCommandHandler;
import xyz.f3l1x.core.audio_book.command.update.UpdateAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.update.UpdateAudioBookCommandHandler;
import xyz.f3l1x.core.audio_book.query.find_all.FindAllAudioBookQuery;
import xyz.f3l1x.core.audio_book.query.find_all.FindAllAudioBookQueryHandler;
import xyz.f3l1x.core.genre.IGenreRepository;
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
    public IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> audioBookQueryService(IAudioBookRepository audioBookRepository) {
        return new FindAllAudioBookQueryHandler(audioBookRepository);
    }

    @Bean
    public ICommandHandler<CreateAudioBookCommand, AudioBook> createAudioBookCommandHandler(IAudioBookRepository audioBookRepository, IGenreRepository genreRepository) {
        return new CreateAudioBookCommandHandler(audioBookRepository, genreRepository);
    }

    @Bean
    public ICommandHandler<UpdateAudioBookCommand, AudioBook> updateAudioBookCommandHandler(IAudioBookRepository audioBookRepository) {
        return new UpdateAudioBookCommandHandler(audioBookRepository);
    }
}
