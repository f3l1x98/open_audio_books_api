package xyz.f3l1x.core.genre.exception;

import java.util.UUID;

public class GenreNotFoundException extends Exception {
    public GenreNotFoundException(UUID id) {
        super("Genre with id " + id + " not found");
    }
}
