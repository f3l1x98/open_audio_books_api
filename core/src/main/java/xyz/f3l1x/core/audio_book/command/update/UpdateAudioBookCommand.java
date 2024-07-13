package xyz.f3l1x.core.audio_book.command.update;

import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.genre.Genre;

import java.util.Date;
import java.util.List;
import java.util.Set;

public record UpdateAudioBookCommand(
        Long id,
        String title,
        String summary,
        Date releaseDate,
        Boolean ongoing,
        Integer rating
) {
}
