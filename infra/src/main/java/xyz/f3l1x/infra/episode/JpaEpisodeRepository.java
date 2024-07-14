package xyz.f3l1x.infra.episode;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JpaEpisodeRepository implements IEpisodeRepository {
    private final ModelMapper mapper;
    private final IJpaEpisodeRepository repository;

    public JpaEpisodeRepository(ModelMapper mapper, IJpaEpisodeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Episode save(Episode episode) {
        EpisodeEntity saved = this.repository.save(this.mapper.map(episode, EpisodeEntity.class));
        return mapper.map(saved, Episode.class);
    }

    @Override
    public Optional<Episode> findById(UUID id) {
        Optional<EpisodeEntity> entity = this.repository.findById(id);
        return mapper.map(entity, new TypeToken<Optional<Episode>>() {}.getType());
    }

    @Override
    public List<Episode> findAllByIdIn(List<UUID> ids) {
        List<EpisodeEntity> entities = repository.findAllByIdIn(ids);
        return this.mapper.map(entities, new TypeToken<List<Episode>>() {}.getType());
    }

    @Override
    public List<Episode> findAllForAudioBook(UUID audioBookId) {
        List<EpisodeEntity> entities = repository.findAllByAudioBook_Id(audioBookId);
        return this.mapper.map(entities, new TypeToken<List<Episode>>() {}.getType());
    }

    @Override
    public void delete(Episode episode) {
        repository.delete(mapper.map(episode, EpisodeEntity.class));
    }
}
