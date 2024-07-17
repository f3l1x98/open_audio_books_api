package xyz.f3l1x.infra.author;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.IAuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JpaAuthorRepository implements IAuthorRepository {
    private final ModelMapper mapper;
    private final IJpaAuthorRepository repository;

    public JpaAuthorRepository(ModelMapper mapper, IJpaAuthorRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Author save(Author model) {
        AuthorEntity saved = this.repository.save(this.mapper.map(model, AuthorEntity.class));
        return mapper.map(saved, Author.class);
    }

    @Override
    public Optional<Author> findById(UUID id) {
        Optional<AuthorEntity> entity = this.repository.findById(id);
        return mapper.map(entity, new TypeToken<Optional<Author>>() {}.getType());
    }

    @Override
    public List<Author> findAll() {
        List<AuthorEntity> entities = repository.findAll();
        return mapper.map(entities, new TypeToken<List<Author>>() {}.getType());
    }

    @Override
    public List<Author> findAllByIdIn(List<UUID> ids) {
        List<AuthorEntity> entities = repository.findAllByIdIn(ids);
        return this.mapper.map(entities, new TypeToken<List<Author>>() {}.getType());
    }

    @Override
    public List<Author> findAllForAudioBook(UUID audioBookId) {
        List<AuthorEntity> entities = repository.findAllByAudioBooks_Id(audioBookId);
        return mapper.map(entities, new TypeToken<List<Author>>() {}.getType());
    }

    @Override
    public void delete(Author genre) {
        repository.delete(mapper.map(genre, AuthorEntity.class));
    }
}
