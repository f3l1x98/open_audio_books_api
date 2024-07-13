package xyz.f3l1x.core.episode.command.update;

import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.episode.exception.EpisodeNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class UpdateEpisodeCommandHandler implements ICommandHandler<UpdateEpisodeCommand, Episode> {
    private final IEpisodeRepository episodeRepository;

    public UpdateEpisodeCommandHandler(IEpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @Override
    public Episode handle(UpdateEpisodeCommand command) throws Exception {
        Optional<Episode> episodeOptional = episodeRepository.findById(command.episodeId());

        if (episodeOptional.isEmpty()) {
            throw new EpisodeNotFoundException(command.episodeId());
        }

        Episode episode = episodeOptional.get();
        episode.update(command.number(), command.title(), command.summary(), command.releaseDate());

        return episodeRepository.save(episode);
    }
}
