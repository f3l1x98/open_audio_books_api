package xyz.f3l1x.core.audio_book.command.update;

import java.util.Date;
import java.util.UUID;

public record UpdateAudioBookCommand(
        UUID id,
        String title,
        String summary,
        Date releaseDate,
        Boolean ongoing,
        Integer rating
) {
}
