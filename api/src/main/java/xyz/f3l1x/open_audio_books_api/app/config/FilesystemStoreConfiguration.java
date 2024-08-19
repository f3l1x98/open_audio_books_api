package xyz.f3l1x.open_audio_books_api.app.config;

import org.springframework.content.fs.config.FilesystemStoreConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import xyz.f3l1x.infra.episode.EpisodeEntity;

import java.nio.file.Path;

@Configuration
public class FilesystemStoreConfiguration  {
    @Bean
    public FilesystemStoreConfigurer configurer() {
        return registry -> registry.addConverter(new Converter<EpisodeEntity, String>() {

            @Override
            public String convert(EpisodeEntity episodeEntity) {
                return Path.of(episodeEntity.getAudioBook().getDirectoryName(), episodeEntity.getFileName()).toString();
            }
        });
    }
}
