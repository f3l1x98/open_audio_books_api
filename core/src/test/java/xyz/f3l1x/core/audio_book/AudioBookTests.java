package xyz.f3l1x.core.audio_book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.f3l1x.core.audio_book.exception.UniqueEpisodeViolationException;
import xyz.f3l1x.core.episode.Episode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class AudioBookTests {

    @Test
    public void testAddNewEpisode_success() {
        AudioBook audioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>());
        Episode expectedEpisode = new Episode(0, "Mock episode title", null, new Date(), audioBook);

        Assertions.assertDoesNotThrow(() -> {
            int beforeEpisodesCount = audioBook.getEpisodes().size();
            Episode result = audioBook.addNewEpisode(expectedEpisode.getNumber(), expectedEpisode.getTitle(), expectedEpisode.getSummary(), expectedEpisode.getReleaseDate());
            int afterEpisodesCount = audioBook.getEpisodes().size();

            Assertions.assertEquals(result.getNumber(), expectedEpisode.getNumber());
            Assertions.assertEquals(result.getTitle(), expectedEpisode.getTitle());
            Assertions.assertEquals(result.getSummary(), expectedEpisode.getSummary());
            Assertions.assertEquals(result.getReleaseDate(), expectedEpisode.getReleaseDate());
            Assertions.assertEquals(result.getAudioBook(), expectedEpisode.getAudioBook());
            Assertions.assertEquals(beforeEpisodesCount + 1, afterEpisodesCount);
            assertThat(audioBook.getEpisodes(), contains(result));
        });
    }

    @Test
    public void testAddNewEpisode_duplicateNumber() {
        Episode episode = new Episode(0, "Mock episode 0", null, new Date(), null);
        AudioBook audioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(List.of(episode)),
                new HashSet<>());
        Episode expectedEpisode = new Episode(0, "Mock episode title", null, new Date(), audioBook);

        int beforeEpisodesCount = audioBook.getEpisodes().size();
        Assertions.assertThrows(UniqueEpisodeViolationException.class, () -> {
            audioBook.addNewEpisode(expectedEpisode.getNumber(), expectedEpisode.getTitle(), expectedEpisode.getSummary(), expectedEpisode.getReleaseDate());
        });
        int afterEpisodesCount = audioBook.getEpisodes().size();

        Assertions.assertEquals(beforeEpisodesCount, afterEpisodesCount);
    }

    @Test
    public void testAddNewEpisode_duplicateTitle() {
        Episode episode = new Episode(0, "Mock episode 0", null, new Date(), null);
        AudioBook audioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(List.of(episode)),
                new HashSet<>());
        Episode expectedEpisode = new Episode(1, "Mock episode 0", null, new Date(), audioBook);

        int beforeEpisodesCount = audioBook.getEpisodes().size();
        Assertions.assertThrows(UniqueEpisodeViolationException.class, () -> {
            audioBook.addNewEpisode(expectedEpisode.getNumber(), expectedEpisode.getTitle(), expectedEpisode.getSummary(), expectedEpisode.getReleaseDate());
        });
        int afterEpisodesCount = audioBook.getEpisodes().size();

        Assertions.assertEquals(beforeEpisodesCount, afterEpisodesCount);
    }
}
