package xyz.f3l1x.core.audio_book.exception;

public class AuthorRequiredException extends Exception {
    public AuthorRequiredException() {
        super("At least one author is required");
    }
}
