package xyz.f3l1x.open_audio_books_api.config.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreBeans {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // TODO Not sure yet if required
        /*TypeMap<AudioBook, AudioBookDto> audioBookDtoTypeMap = mapper.createTypeMap(AudioBook.class, AudioBookDto.class);
        audioBookDtoTypeMap.addMappings(m -> m.using(new BaseModelCollectionToIdCollectionConverter()).map(AudioBook::getEpisodes, AudioBookDto::setEpisodeIds));
        audioBookDtoTypeMap.addMappings(m -> m.using(new BaseModelCollectionToIdCollectionConverter()).map(AudioBook::getGenres, AudioBookDto::setGenreIds));
        audioBookDtoTypeMap.addMappings(m -> m.using(new BaseModelCollectionToIdCollectionConverter()).map(AudioBook::getAuthors, AudioBookDto::setAuthorIds));
        audioBookDtoTypeMap.addMappings(m -> m.using(new BaseModelCollectionToIdCollectionConverter()).map(AudioBook::getNarrators, AudioBookDto::setNarratorIds));*/

        return mapper;
    }
}
