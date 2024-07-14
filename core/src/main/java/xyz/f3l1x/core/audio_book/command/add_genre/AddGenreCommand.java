package xyz.f3l1x.core.audio_book.command.add_genre;

import java.util.UUID;

public record AddGenreCommand(UUID audioBookId, UUID genreId) {
}
