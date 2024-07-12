package xyz.f3l1x.core.genre.exception;

public class GenreNotFoundException extends Exception {
    public GenreNotFoundException(Long id) {
        super("Genre with id " + id + " not found");
    }
}
