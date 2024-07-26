package xyz.f3l1x.core.audio_book.command.create;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;
import xyz.f3l1x.core.author.IAuthorRepository;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.shared.BaseModel;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateAudioBookCommandHandlerTests {

    private CreateAudioBookCommandHandler commandHandler;

    @Mock
    private IAudioBookRepository audioBookRepository;
    @Mock
    private IGenreRepository genreRepository;
    @Mock
    private IAuthorRepository authorRepository;

    @BeforeEach
    public void init() {
        this.commandHandler = new CreateAudioBookCommandHandler(audioBookRepository, genreRepository, authorRepository);
    }

    @Test
    public void testHandle_success() {
        AudioBook expectedAudioBook = new AudioBook(
                "Mock Title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>());
        CreateAudioBookCommand command = new CreateAudioBookCommand(
                expectedAudioBook.getTitle(),
                expectedAudioBook.getSummary(),
                expectedAudioBook.getReleaseDate(),
                expectedAudioBook.getOngoing(),
                expectedAudioBook.getRating(),
                new ArrayList<>(),
                new ArrayList<>());

        when(genreRepository.findAllByIdIn(command.genreIds())).thenReturn(new ArrayList<>());
        when(authorRepository.findAllByIdIn(command.authorIds())).thenReturn(new ArrayList<>());
        when(audioBookRepository.save(refEq(expectedAudioBook, "id"))).thenReturn(expectedAudioBook);

        Assertions.assertDoesNotThrow(() -> {
            AudioBook result = commandHandler.handle(command);

            verify(genreRepository).findAllByIdIn(command.genreIds());
            Assertions.assertEquals(result, expectedAudioBook);
        });
    }

    @Test
    public void testHandle_success_withGenres() {
        Genre mockGenre = new Genre("Mock Genre");
        AudioBook expectedAudioBook = new AudioBook(
                "Mock Title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                Set.of(mockGenre),
                new ArrayList<>());
        CreateAudioBookCommand command = new CreateAudioBookCommand(
                expectedAudioBook.getTitle(),
                expectedAudioBook.getSummary(),
                expectedAudioBook.getReleaseDate(),
                expectedAudioBook.getOngoing(),
                expectedAudioBook.getRating(),
                expectedAudioBook.getGenres().stream().map(BaseModel::getId).toList(),
                new ArrayList<>());

        when(genreRepository.findAllByIdIn(command.genreIds())).thenReturn(List.of(mockGenre));
        when(authorRepository.findAllByIdIn(command.authorIds())).thenReturn(new ArrayList<>());
        when(audioBookRepository.save(refEq(expectedAudioBook, "id"))).thenReturn(expectedAudioBook);

        Assertions.assertDoesNotThrow(() -> {
            AudioBook result = commandHandler.handle(command);

            verify(genreRepository).findAllByIdIn(command.genreIds());
            Assertions.assertEquals(result, expectedAudioBook);
        });
    }

    @Test
    public void testHandle_success_withGenres_genreNotFound() {
        Genre mockGenre = new Genre("Mock Genre");
        AudioBook expectedAudioBook = new AudioBook(
                "Mock Title",
                "Mock summary",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>());
        CreateAudioBookCommand command = new CreateAudioBookCommand(
                expectedAudioBook.getTitle(),
                expectedAudioBook.getSummary(),
                expectedAudioBook.getReleaseDate(),
                expectedAudioBook.getOngoing(),
                expectedAudioBook.getRating(),
                List.of(mockGenre.getId()),
                new ArrayList<>());

        when(genreRepository.findAllByIdIn(command.genreIds())).thenReturn(new ArrayList<Genre>());
        when(authorRepository.findAllByIdIn(command.authorIds())).thenReturn(new ArrayList<>());
        when(audioBookRepository.save(refEq(expectedAudioBook, "id"))).thenReturn(expectedAudioBook);

        Assertions.assertDoesNotThrow(() -> {
            AudioBook result = commandHandler.handle(command);

            verify(genreRepository).findAllByIdIn(command.genreIds());
            Assertions.assertEquals(result, expectedAudioBook);
        });
    }
}
