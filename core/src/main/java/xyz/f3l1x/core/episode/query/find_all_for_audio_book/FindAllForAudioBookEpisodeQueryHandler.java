package xyz.f3l1x.core.episode.query.find_all_for_audio_book;

import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;

public class FindAllForAudioBookEpisodeQueryHandler implements IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> {
    private final IEpisodeRepository episodeRepository;

    public FindAllForAudioBookEpisodeQueryHandler(IEpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @Override
    public List<Episode> handle(FindAllForAudioBookEpisodeQuery query) {
        return episodeRepository.findAllForAudioBook(query.audioBookId());
    }
}
