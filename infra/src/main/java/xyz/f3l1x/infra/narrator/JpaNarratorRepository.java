package xyz.f3l1x.infra.narrator;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.narrator.Narrator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JpaNarratorRepository implements INarratorRepository {
    private final ModelMapper mapper;
    private final IJpaNarratorRepository repository;

    public JpaNarratorRepository(ModelMapper mapper, IJpaNarratorRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Narrator save(Narrator model) {
        NarratorEntity saved = this.repository.save(this.mapper.map(model, NarratorEntity.class));
        return mapper.map(saved, Narrator.class);
    }

    @Override
    public Optional<Narrator> findById(UUID id) {
        Optional<NarratorEntity> entity = this.repository.findById(id);
        return mapper.map(entity, new TypeToken<Optional<Narrator>>() {}.getType());
    }

    @Override
    public List<Narrator> findAll() {
        List<NarratorEntity> entities = repository.findAll();
        return mapper.map(entities, new TypeToken<List<Narrator>>() {}.getType());
    }

    @Override
    public List<Narrator> findAllByIdIn(List<UUID> ids) {
        List<NarratorEntity> entities = repository.findAllByIdIn(ids);
        return this.mapper.map(entities, new TypeToken<List<Narrator>>() {}.getType());
    }

    @Override
    public List<Narrator> findAllForAudioBook(UUID audioBookId) {
        List<NarratorEntity> entities = repository.findAllByAudioBooks_Id(audioBookId);
        return mapper.map(entities, new TypeToken<List<Narrator>>() {}.getType());
    }

    @Override
    public void delete(Narrator narrator) {
        repository.delete(mapper.map(narrator, NarratorEntity.class));
    }
}
