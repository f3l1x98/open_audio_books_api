package xyz.f3l1x.core.episode.command.update;

import java.util.Date;

public record UpdateEpisodeCommand(Long episodeId, Integer number, String title, String summary, Date releaseDate) {
}
