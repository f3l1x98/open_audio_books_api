package xyz.f3l1x.core.audio_book.command.add_genre;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.genre.exception.GenreNotFoundException;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddGenreCommandHandlerTests {

    private AddGenreCommandHandler commandHandler;

    @Mock
    private IGenreRepository genreRepository;
    @Mock
    private IAudioBookRepository audioBookRepository;

    @BeforeEach
    public void init() {
        commandHandler = new AddGenreCommandHandler(genreRepository, audioBookRepository);
    }

    @Test
    public void testHandle_success() {
        Genre mockGenre = new Genre("Mock genre");
        AudioBook mockAudioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>());
        AddGenreCommand command = new AddGenreCommand(mockAudioBook.getId(), mockGenre.getId());

        when(audioBookRepository.findById(command.audioBookId())).thenReturn(Optional.of(mockAudioBook));
        when(genreRepository.findById(command.genreId())).thenReturn(Optional.of(mockGenre));

        Assertions.assertDoesNotThrow(() -> {
            int beforeGenresCount = mockAudioBook.getGenres().size();
            commandHandler.handle(command);
            int afterGenresCount = mockAudioBook.getGenres().size();

            verify(audioBookRepository).save(any(AudioBook.class));
            Assertions.assertEquals(beforeGenresCount + 1, afterGenresCount);
        });
    }

    @Test
    public void testHandle_success_duplicateGenre() {
        Genre mockGenre = new Genre("Mock genre");
        AudioBook mockAudioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(List.of(mockGenre)),
                new ArrayList<>(),
                new ArrayList<>());
        AddGenreCommand command = new AddGenreCommand(mockAudioBook.getId(), mockGenre.getId());

        when(audioBookRepository.findById(command.audioBookId())).thenReturn(Optional.of(mockAudioBook));
        when(genreRepository.findById(command.genreId())).thenReturn(Optional.of(mockGenre));

        Assertions.assertDoesNotThrow(() -> {
            int beforeGenresCount = mockAudioBook.getGenres().size();
            commandHandler.handle(command);
            int afterGenresCount = mockAudioBook.getGenres().size();

            verify(audioBookRepository).save(any(AudioBook.class));
            Assertions.assertEquals(beforeGenresCount, afterGenresCount);
        });
    }

    @Test
    public void testHandle_audioBookNotFound() {
        AudioBook mockAudioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>());
        Genre mockGenre = new Genre("Mock genre");
        AddGenreCommand command = new AddGenreCommand(mockAudioBook.getId(), mockGenre.getId());

        when(audioBookRepository.findById(command.audioBookId())).thenReturn(Optional.empty());

        Assertions.assertThrows(AudioBookNotFoundException.class, () -> {
            commandHandler.handle(command);
        });
        verify(audioBookRepository, never()).save(any(AudioBook.class));
    }

    @Test
    public void testHandle_genreNotFound() {
        AudioBook mockAudioBook = new AudioBook(
                "Mock title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>());
        Genre mockGenre = new Genre("Mock genre");
        AddGenreCommand command = new AddGenreCommand(mockAudioBook.getId(), mockGenre.getId());

        when(audioBookRepository.findById(command.audioBookId())).thenReturn(Optional.of(mockAudioBook));
        when(genreRepository.findById(command.genreId())).thenReturn(Optional.empty());

        Assertions.assertThrows(GenreNotFoundException.class, () -> {
            commandHandler.handle(command);
        });
        verify(audioBookRepository, never()).save(any(AudioBook.class));
    }
}
