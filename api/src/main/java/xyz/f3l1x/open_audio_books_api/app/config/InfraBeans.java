package xyz.f3l1x.open_audio_books_api.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.infra.audio_book.IJpaAudioBookRepository;
import xyz.f3l1x.infra.audio_book.JpaAudioBookRepository;
import xyz.f3l1x.infra.author.IJpaAuthorRepository;
import xyz.f3l1x.infra.author.JpaAuthorRepository;
import xyz.f3l1x.infra.episode.IJpaEpisodeRepository;
import xyz.f3l1x.infra.episode.JpaEpisodeRepository;
import xyz.f3l1x.infra.genre.IJpaGenreRepository;
import xyz.f3l1x.infra.genre.JpaGenreRepository;
import xyz.f3l1x.infra.narrator.IJpaNarratorRepository;
import xyz.f3l1x.infra.narrator.JpaNarratorRepository;

@Configuration()
@EnableJpaRepositories(basePackages = "xyz.f3l1x.infra")
@EntityScan(basePackages = "xyz.f3l1x.infra")
class InfraBeans {

    @Bean
    public IAudioBookRepository audioBookRepository(ModelMapper modelMapper, IJpaAudioBookRepository jpaAudioBookRepository) {
        return new JpaAudioBookRepository(modelMapper, jpaAudioBookRepository);
    }

    @Bean
    public IEpisodeRepository episodeRepository(ModelMapper modelMapper, IJpaEpisodeRepository jpaEpisodeRepository) {
        return new JpaEpisodeRepository(modelMapper, jpaEpisodeRepository);
    }

    @Bean
    public IGenreRepository genreRepository(ModelMapper modelMapper, IJpaGenreRepository jpaGenreRepository) {
        return new JpaGenreRepository(modelMapper, jpaGenreRepository);
    }

    @Bean
    public IAuthorRepository authorRepository(ModelMapper modelMapper, IJpaAuthorRepository jpaAuthorRepository) {
        return new JpaAuthorRepository(modelMapper, jpaAuthorRepository);
    }

    @Bean
    public INarratorRepository narratorRepository(ModelMapper modelMapper, IJpaNarratorRepository jpaNarratorRepository) {
        return new JpaNarratorRepository(modelMapper, jpaNarratorRepository);
    }
}
