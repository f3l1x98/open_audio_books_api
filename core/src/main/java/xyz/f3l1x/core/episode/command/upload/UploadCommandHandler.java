package xyz.f3l1x.core.episode.command.upload;

import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.episode.IEpisodeStore;
import xyz.f3l1x.core.episode.exception.EpisodeNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class UploadCommandHandler implements ICommandHandler<UploadCommand, Episode> {
    private final IEpisodeRepository repository;
    private final IEpisodeStore store;

    public UploadCommandHandler(IEpisodeRepository repository, IEpisodeStore store) {
        this.repository = repository;
        this.store = store;
    }

    @Override
    public Episode handle(UploadCommand command) throws Exception {
        Optional<Episode> episodeOptional = repository.findById(command.episodeId());
        if (episodeOptional.isEmpty()) {
            throw new EpisodeNotFoundException(command.episodeId());
        }

        final Episode ret = store.setContent(episodeOptional.get(), command.input());// new FileInputStream("G:\\felix\\Music\\TEST.mp3")
        ret.setMimeType(command.mimeType());
        return repository.save(ret);
    }
}
