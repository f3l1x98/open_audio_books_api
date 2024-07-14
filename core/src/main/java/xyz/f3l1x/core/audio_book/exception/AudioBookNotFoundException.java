package xyz.f3l1x.core.audio_book.exception;

import java.util.UUID;

public class AudioBookNotFoundException extends Exception {
    public AudioBookNotFoundException(UUID id) {
        super("AudioBook with id " + id + " not found");
    }
}
