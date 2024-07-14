package xyz.f3l1x.core.episode.exception;

import java.util.UUID;

public class EpisodeNotFoundException extends Exception {
    public EpisodeNotFoundException(UUID id) {
        super("Episode with id " + id + " not found");
    }
}
