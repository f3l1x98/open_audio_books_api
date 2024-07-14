package xyz.f3l1x.core.audio_book.command.remove_genre;

import java.util.UUID;

public record RemoveGenreCommand(UUID audioBookId, UUID genreId) {
}
