package xyz.f3l1x.infra.genre;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Genre> findById(UUID id) {
        Optional<GenreEntity> entity = this.repository.findById(id);
        return mapper.map(entity, new TypeToken<Optional<Genre>>() {}.getType());
    }

    @Override
    public List<Genre> findAll() {
        List<GenreEntity> entities = repository.findAll();
        return mapper.map(entities, new TypeToken<List<Genre>>() {}.getType());
    }

    @Override
    public List<Genre> findAllByIdIn(List<UUID> ids) {
        List<GenreEntity> entities = repository.findAllByIdIn(ids);
        return this.mapper.map(entities, new TypeToken<List<Genre>>() {}.getType());
    }

    @Override
    public List<Genre> findAllForAudioBook(UUID audioBookId) {
        List<GenreEntity> entities = repository.findAllByAudioBooks_Id(audioBookId);
        return mapper.map(entities, new TypeToken<List<Genre>>() {}.getType());
    }

    @Override
    public void delete(Genre genre) {
        repository.delete(mapper.map(genre, GenreEntity.class));
    }
}
