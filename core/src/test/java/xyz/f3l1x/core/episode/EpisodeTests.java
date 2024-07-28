package xyz.f3l1x.core.episode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class EpisodeTests {

    @Test
    public void testCreate_success() {
        Episode expectedEpisode = new Episode(
                0,
                "Mock episode title",
                null,
                new Date(),
                10L,
                null);

        Assertions.assertDoesNotThrow(() -> {
            Episode result = Episode.create(
                    expectedEpisode.getNumber(),
                    expectedEpisode.getTitle(),
                    expectedEpisode.getSummary(),
                    expectedEpisode.getReleaseDate(),
                    expectedEpisode.getDuration(),
                    expectedEpisode.getAudioBook());

            Assertions.assertEquals(result.getNumber(), expectedEpisode.getNumber());
            Assertions.assertEquals(result.getTitle(), expectedEpisode.getTitle());
            Assertions.assertEquals(result.getSummary(), expectedEpisode.getSummary());
            Assertions.assertEquals(result.getReleaseDate(), expectedEpisode.getReleaseDate());
            Assertions.assertEquals(result.getDuration(), expectedEpisode.getDuration());
            Assertions.assertEquals(result.getAudioBook(), expectedEpisode.getAudioBook());
        });
    }

    @Test
    public void testCreate_negativeDuration() {
        Episode expectedEpisode = new Episode(
                0,
                "Mock episode title",
                null,
                new Date(),
                -1L,
                null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Episode.create(
                    expectedEpisode.getNumber(),
                    expectedEpisode.getTitle(),
                    expectedEpisode.getSummary(),
                    expectedEpisode.getReleaseDate(),
                    expectedEpisode.getDuration(),
                    expectedEpisode.getAudioBook());
        });
    }

    @Test
    public void testCreate_zeroDuration() {
        Episode expectedEpisode = new Episode(
                0,
                "Mock episode title",
                null,
                new Date(),
                0L,
                null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Episode.create(
                    expectedEpisode.getNumber(),
                    expectedEpisode.getTitle(),
                    expectedEpisode.getSummary(),
                    expectedEpisode.getReleaseDate(),
                    expectedEpisode.getDuration(),
                    expectedEpisode.getAudioBook());
        });
    }
}
