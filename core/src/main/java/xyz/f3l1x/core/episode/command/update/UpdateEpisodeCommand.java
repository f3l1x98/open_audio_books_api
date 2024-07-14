package xyz.f3l1x.core.episode.command.update;

import java.util.Date;
import java.util.UUID;

public record UpdateEpisodeCommand(UUID episodeId, Integer number, String title, String summary, Date releaseDate) {
}
