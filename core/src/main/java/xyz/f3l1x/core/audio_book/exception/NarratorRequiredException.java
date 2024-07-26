package xyz.f3l1x.core.audio_book.exception;

public class NarratorRequiredException extends Exception {
    public NarratorRequiredException() {
        super("At least one narrator is required");
    }
}
