package xyz.f3l1x.core.narrator.exception;

import java.util.UUID;

public class NarratorNotFoundException extends Exception {
    public NarratorNotFoundException(UUID id) {
        super("Narrator with id " + id + " not found");
    }
}
