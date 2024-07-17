package xyz.f3l1x.core.author.exception;

import java.util.UUID;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException(UUID id) {
        super("Author with id " + id + " not found");
    }
}
