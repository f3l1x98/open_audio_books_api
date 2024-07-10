package xyz.f3l1x.core.audio_book.command.add_new_episode;

import java.util.Date;

public record AddNewEpisodeCommand(Long audioBookId, Integer number, String title, String summary, Date releaseDate) {
}
