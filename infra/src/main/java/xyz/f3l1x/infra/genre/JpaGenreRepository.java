package xyz.f3l1x.infra.genre;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.infra.episode.EpisodeEntity;

import java.util.List;

public class JpaGenreRepository implements IGenreRepository {
    private final ModelMapper mapper;
    private final IJpaGenreRepository repository;

    public JpaGenreRepository(ModelMapper mapper, IJpaGenreRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Genre save(Genre model) {
        GenreEntity saved = this.repository.save(this.mapper.map(model, GenreEntity.class));
        return mapper.map(saved, Genre.class);
    }

    @Override
    public List<Genre> findAllByIdIn(List<Long> ids) {
        List<GenreEntity> entities = repository.findAllByIdIn(ids);
        return this.mapper.map(entities, new TypeToken<List<Genre>>() {}.getType());
    }
}
