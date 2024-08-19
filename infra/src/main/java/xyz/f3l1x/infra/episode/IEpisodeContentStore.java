package xyz.f3l1x.infra.episode;

import org.springframework.content.commons.store.ContentStore;

import java.util.UUID;

public interface IEpisodeContentStore extends ContentStore<EpisodeEntity, UUID> {
}
