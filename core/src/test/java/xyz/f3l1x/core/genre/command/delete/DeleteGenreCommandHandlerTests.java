package xyz.f3l1x.core.genre.command.delete;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.IGenreRepository;
import xyz.f3l1x.core.genre.exception.GenreNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteGenreCommandHandlerTests {

    private DeleteGenreCommandHandler commandHandler;

    @Mock
    private IGenreRepository genreRepository;

    @BeforeEach
    public void init() {
        commandHandler = new DeleteGenreCommandHandler(genreRepository);
    }

    @Test
    public void testHandle_success() {
        Genre mockGenre = new Genre("Mock genre");
        DeleteGenreCommand command = new DeleteGenreCommand(mockGenre.getId());

        when(genreRepository.findById(command.genreId())).thenReturn(Optional.of(mockGenre));

        Assertions.assertDoesNotThrow(() -> {
            commandHandler.handle(command);

            verify(genreRepository).delete(any(Genre.class));
        });
    }

    @Test
    public void testHandle_genreNotFound() {
        Genre mockGenre = new Genre("Mock genre");
        DeleteGenreCommand command = new DeleteGenreCommand(mockGenre.getId());

        when(genreRepository.findById(command.genreId())).thenReturn(Optional.empty());

        Assertions.assertThrows(GenreNotFoundException.class, () -> {
            commandHandler.handle(command);
        });

        verify(genreRepository, never()).delete(any(Genre.class));
    }
}
