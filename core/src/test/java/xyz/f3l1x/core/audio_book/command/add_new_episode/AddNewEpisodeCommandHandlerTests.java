package xyz.f3l1x.core.audio_book.command.add_new_episode;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.audio_book.exception.UniqueEpisodeViolationException;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.genre.Genre;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AddNewEpisodeCommandHandlerTests {

    private AddNewEpisodeCommandHandler commandHandler;

    @Mock
    private IAudioBookRepository audioBookRepository;

    private AudioBook mockAudioBook;

    @BeforeEach
    public void init() {
        Episode mockEpisode = new Episode(0, "Episode 0", null, new Date(), null);
        mockAudioBook = new AudioBook("Mock title", "Mock summary", new Date(), true, 0, List.of(mockEpisode), new HashSet<Genre>());

        this.commandHandler = new AddNewEpisodeCommandHandler(audioBookRepository);
    }

    @Test
    public void testHandle_validNewEpisode() {
        AddNewEpisodeCommand command = new AddNewEpisodeCommand(mockAudioBook.getId(), 1, "Episode 1", null, new Date());

        when(audioBookRepository.findById(command.audioBookId())).thenReturn(Optional.of(mockAudioBook));

        Assertions.assertDoesNotThrow(() -> {
            int beforeEpisodesCount = mockAudioBook.getEpisodes().size();
            commandHandler.handle(command);
            int afterEpisodesCount = mockAudioBook.getEpisodes().size();

            Assertions.assertEquals(beforeEpisodesCount + 1, afterEpisodesCount);
        });
    }

    @Test
    public void testHandle_audioBookNotFound() {
        AddNewEpisodeCommand command = new AddNewEpisodeCommand(mockAudioBook.getId(), 0, "Episode 1", null, new Date());

        when(audioBookRepository.findById(command.audioBookId())).thenReturn(Optional.empty());

        Assertions.assertThrows(AudioBookNotFoundException.class, () -> {
            int beforeEpisodesCount = mockAudioBook.getEpisodes().size();
            commandHandler.handle(command);
            int afterEpisodesCount = mockAudioBook.getEpisodes().size();

            Assertions.assertEquals(beforeEpisodesCount + 1, afterEpisodesCount);
        });
    }

    @Test
    public void testHandle_duplicateNumber() {
        AddNewEpisodeCommand command = new AddNewEpisodeCommand(mockAudioBook.getId(), 0, "Episode 1", null, new Date());

        when(audioBookRepository.findById(command.audioBookId())).thenReturn(Optional.of(mockAudioBook));

        Assertions.assertThrows(UniqueEpisodeViolationException.class, () -> {
            int beforeEpisodesCount = mockAudioBook.getEpisodes().size();
            commandHandler.handle(command);
            int afterEpisodesCount = mockAudioBook.getEpisodes().size();

            Assertions.assertEquals(beforeEpisodesCount + 1, afterEpisodesCount);
        });
    }

    @Test
    public void testHandle_duplicateTitle() {
        AddNewEpisodeCommand command = new AddNewEpisodeCommand(mockAudioBook.getId(), 0, "Episode 1", null, new Date());

        when(audioBookRepository.findById(command.audioBookId())).thenReturn(Optional.of(mockAudioBook));

        Assertions.assertThrows(UniqueEpisodeViolationException.class, () -> {
            int beforeEpisodesCount = mockAudioBook.getEpisodes().size();
            commandHandler.handle(command);
            int afterEpisodesCount = mockAudioBook.getEpisodes().size();

            Assertions.assertEquals(beforeEpisodesCount + 1, afterEpisodesCount);
        });
    }
}
