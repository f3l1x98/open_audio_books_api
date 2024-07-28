package xyz.f3l1x.core.audio_book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.f3l1x.core.audio_book.exception.AuthorRequiredException;
import xyz.f3l1x.core.audio_book.exception.NarratorRequiredException;
import xyz.f3l1x.core.audio_book.exception.UniqueEpisodeViolationException;
import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.narrator.Narrator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class AudioBookTests {

    @Test
    public void testCreate_success() {
        Author mockAuthor = new Author("Mock", "Mock", "Mock", new ArrayList<>());
        Narrator mockNarrator = new Narrator("Mock", "Mock", "Mock", new ArrayList<>());
        AudioBook expectedAudioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                List.of(mockAuthor),
                List.of(mockNarrator));

        Assertions.assertDoesNotThrow(() -> {
            AudioBook result = AudioBook.create(
                    expectedAudioBook.getTitle(),
                    expectedAudioBook.getSummary(),
                    expectedAudioBook.getReleaseDate(),
                    expectedAudioBook.getOngoing(),
                    expectedAudioBook.getRating(),
                    new ArrayList<>(expectedAudioBook.getGenres()),
                    expectedAudioBook.getAuthors(),
                    expectedAudioBook.getNarrators());

            Assertions.assertEquals(result.getTitle(), expectedAudioBook.getTitle());
            Assertions.assertEquals(result.getSummary(), expectedAudioBook.getSummary());
            Assertions.assertEquals(result.getReleaseDate(), expectedAudioBook.getReleaseDate());
            Assertions.assertEquals(result.getOngoing(), expectedAudioBook.getOngoing());
            Assertions.assertEquals(result.getRating(), expectedAudioBook.getRating());
            Assertions.assertEquals(result.getGenres(), expectedAudioBook.getGenres());
            Assertions.assertEquals(result.getAuthors(), expectedAudioBook.getAuthors());
            Assertions.assertEquals(result.getNarrators(), expectedAudioBook.getNarrators());
        });
    }

    @Test
    public void testCreate_authorRequired() {
        Narrator mockNarrator = new Narrator("Mock", "Mock", "Mock", new ArrayList<>());
        AudioBook expectedAudioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>(),
                List.of(mockNarrator));

        Assertions.assertThrows(AuthorRequiredException.class, () -> {
            AudioBook.create(
                    expectedAudioBook.getTitle(),
                    expectedAudioBook.getSummary(),
                    expectedAudioBook.getReleaseDate(),
                    expectedAudioBook.getOngoing(),
                    expectedAudioBook.getRating(),
                    new ArrayList<>(expectedAudioBook.getGenres()),
                    expectedAudioBook.getAuthors(),
                    expectedAudioBook.getNarrators());
        });
    }

    @Test
    public void testCreate_narratorRequired() {
        Author mockAuthor = new Author("Mock", "Mock", "Mock", new ArrayList<>());
        AudioBook expectedAudioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                List.of(mockAuthor),
                new ArrayList<>());

        Assertions.assertThrows(NarratorRequiredException.class, () -> {
            AudioBook.create(
                    expectedAudioBook.getTitle(),
                    expectedAudioBook.getSummary(),
                    expectedAudioBook.getReleaseDate(),
                    expectedAudioBook.getOngoing(),
                    expectedAudioBook.getRating(),
                    new ArrayList<>(expectedAudioBook.getGenres()),
                    expectedAudioBook.getAuthors(),
                    expectedAudioBook.getNarrators());
        });
    }

    @Test
    public void testAddNewEpisode_success() {
        AudioBook audioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>());
        Episode expectedEpisode = new Episode(0, "Mock episode title", null, new Date(), 10L, audioBook);

        Assertions.assertDoesNotThrow(() -> {
            int beforeEpisodesCount = audioBook.getEpisodes().size();
            Episode result = audioBook.addNewEpisode(expectedEpisode.getNumber(), expectedEpisode.getTitle(), expectedEpisode.getSummary(), expectedEpisode.getReleaseDate(), expectedEpisode.getDuration());
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
        Episode episode = new Episode(0, "Mock episode 0", null, new Date(), 10L, null);
        AudioBook audioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(List.of(episode)),
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>());
        Episode expectedEpisode = new Episode(0, "Mock episode title", null, new Date(), 10L, audioBook);

        int beforeEpisodesCount = audioBook.getEpisodes().size();
        Assertions.assertThrows(UniqueEpisodeViolationException.class, () -> {
            audioBook.addNewEpisode(expectedEpisode.getNumber(), expectedEpisode.getTitle(), expectedEpisode.getSummary(), expectedEpisode.getReleaseDate(), expectedEpisode.getDuration());
        });
        int afterEpisodesCount = audioBook.getEpisodes().size();

        Assertions.assertEquals(beforeEpisodesCount, afterEpisodesCount);
    }

    @Test
    public void testAddNewEpisode_duplicateTitle() {
        Episode episode = new Episode(0, "Mock episode 0", null, new Date(), 10L, null);
        AudioBook audioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(List.of(episode)),
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>());
        Episode expectedEpisode = new Episode(1, "Mock episode 0", null, new Date(), 10L, audioBook);

        int beforeEpisodesCount = audioBook.getEpisodes().size();
        Assertions.assertThrows(UniqueEpisodeViolationException.class, () -> {
            audioBook.addNewEpisode(expectedEpisode.getNumber(), expectedEpisode.getTitle(), expectedEpisode.getSummary(), expectedEpisode.getReleaseDate(), expectedEpisode.getDuration());
        });
        int afterEpisodesCount = audioBook.getEpisodes().size();

        Assertions.assertEquals(beforeEpisodesCount, afterEpisodesCount);
    }
}
