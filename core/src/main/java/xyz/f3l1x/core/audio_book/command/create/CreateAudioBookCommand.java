package xyz.f3l1x.core.audio_book.command.create;

import java.util.Date;
import java.util.List;

public record CreateAudioBookCommand(
        String title,
        String summary,
        Date releaseDate,
        Boolean ongoing,
        Integer rating,
        List<Long> genreIds
) {
}
