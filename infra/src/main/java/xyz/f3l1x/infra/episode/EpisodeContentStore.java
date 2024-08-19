package xyz.f3l1x.infra.episode;

import org.modelmapper.ModelMapper;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeStore;

import java.io.InputStream;

public class EpisodeContentStore implements IEpisodeStore {
    private final ModelMapper mapper;
    private final IEpisodeContentStore store;

    public EpisodeContentStore(ModelMapper mapper, IEpisodeContentStore store) {
        this.mapper = mapper;
        this.store = store;
    }

    @Override
    public InputStream getContent(Episode episode) {
        EpisodeEntity episodeEntity = mapper.map(episode, EpisodeEntity.class);
        return store.getContent(episodeEntity);
    }

    @Override
    public Episode setContent(Episode episode, InputStream inputStream) {
        EpisodeEntity episodeEntity = mapper.map(episode, EpisodeEntity.class);
        EpisodeEntity updated = store.setContent(episodeEntity, inputStream);
        return mapper.map(updated, Episode.class);
    }
}
