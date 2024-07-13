package xyz.f3l1x.core.episode.exception;

public class EpisodeNotFoundException extends Exception {
    public EpisodeNotFoundException(Long id) {
        super("Episode with id " + id + " not found");
    }
}
