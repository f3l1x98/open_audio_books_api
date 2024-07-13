package xyz.f3l1x.core.episode.command.delete;

import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.episode.exception.EpisodeNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class DeleteEpisodeCommandHandler implements ICommandHandler<DeleteEpisodeCommand, Episode> {
    private final IEpisodeRepository episodeRepository;

    public DeleteEpisodeCommandHandler(IEpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @Override
    public Episode handle(DeleteEpisodeCommand command) throws Exception {
        Optional<Episode> episodeOptional = episodeRepository.findById(command.episodeId());
        if (episodeOptional.isEmpty()) {
            throw new EpisodeNotFoundException(command.episodeId());
        }

        episodeRepository.delete(episodeOptional.get());

        return episodeOptional.get();
    }
}
