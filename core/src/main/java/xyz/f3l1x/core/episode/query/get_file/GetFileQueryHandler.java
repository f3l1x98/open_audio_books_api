package xyz.f3l1x.core.episode.query.get_file;

import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.episode.IEpisodeStore;
import xyz.f3l1x.core.episode.exception.EpisodeNotFoundException;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.io.FileNotFoundException;
import java.util.Optional;

public class GetFileQueryHandler implements IQueryHandler<GetFileQuery, GetFileQueryResult> {
    private final IEpisodeRepository episodeRepository;
    private final IEpisodeStore episodeStore;

    public GetFileQueryHandler(IEpisodeRepository episodeRepository, IEpisodeStore episodeStore) {
        this.episodeRepository = episodeRepository;
        this.episodeStore = episodeStore;
    }

    @Override
    public GetFileQueryResult handle(GetFileQuery query) throws EpisodeNotFoundException, FileNotFoundException {
        Optional<Episode> episodeOptional = episodeRepository.findById(query.episodeId());
        if (episodeOptional.isEmpty()) {
            throw new EpisodeNotFoundException(query.episodeId());
        }

        return new GetFileQueryResult(episodeStore.getContent(episodeOptional.get()), episodeOptional.get().getContentLength(), episodeOptional.get().getMimeType());
    }
}
