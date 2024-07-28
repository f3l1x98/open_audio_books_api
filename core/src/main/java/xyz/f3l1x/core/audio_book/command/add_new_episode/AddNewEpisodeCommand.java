package xyz.f3l1x.core.audio_book.command.add_new_episode;

import java.util.Date;
import java.util.UUID;

public record AddNewEpisodeCommand(UUID audioBookId, Integer number, String title, String summary, Date releaseDate, Long duration) {
}
