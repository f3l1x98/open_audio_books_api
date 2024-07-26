package xyz.f3l1x.core.audio_book.command.create;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CreateAudioBookCommand(
        String title,
        String summary,
        Date releaseDate,
        Boolean ongoing,
        Integer rating,
        List<UUID> genreIds,
        List<UUID> authorIds,
        List<UUID> narratorIds
) {
}
