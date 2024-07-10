package xyz.f3l1x.core.audio_book.exception;

public class AudioBookNotFoundException extends Exception {
    public AudioBookNotFoundException(Long id) {
        super("AudioBook with id " + id + " not found");
    }
}
