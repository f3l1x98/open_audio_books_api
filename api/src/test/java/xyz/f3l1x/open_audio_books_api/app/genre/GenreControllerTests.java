package xyz.f3l1x.open_audio_books_api.app.genre;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.command.create.CreateGenreCommand;
import xyz.f3l1x.core.genre.command.delete.DeleteGenreCommand;
import xyz.f3l1x.core.genre.exception.GenreNotFoundException;
import xyz.f3l1x.core.genre.query.find_all.FindAllGenreQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.genre.dto.CreateGenreRequest;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@Import(ModelMapper.class)
public class GenreControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private IQueryHandler<FindAllGenreQuery, List<Genre>> findAllGenreQueryHandler;
    @MockBean
    private ICommandHandler<CreateGenreCommand, Genre> createGenreCommandHandler;
    @MockBean
    private ICommandHandler<DeleteGenreCommand, Genre> deleteGenreCommandHandler;

    @Test
    public void testGetAllGenres_success() throws Exception {
        List<Genre> mockGenres = new ArrayList<>();
        mockGenres.add(new Genre("Mock Genre 1"));
        mockGenres.add(new Genre("Mock Genre 2"));
        FindAllGenreQuery expectedQuery = new FindAllGenreQuery(Optional.empty());

        when(findAllGenreQueryHandler.handle(expectedQuery)).thenReturn(mockGenres);

        mockMvc.perform(get("/api/v1/genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllGenres_byAudioBookId_success() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        List<Genre> mockGenres = new ArrayList<>();
        mockGenres.add(new Genre("Mock Genre 1"));
        mockGenres.add(new Genre("Mock Genre 2"));
        FindAllGenreQuery expectedQuery = new FindAllGenreQuery(Optional.of(audioBookId));

        when(findAllGenreQueryHandler.handle(expectedQuery)).thenReturn(mockGenres);

        mockMvc.perform(get("/api/v1/genre").param("audioBookId", audioBookId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllGenres_byAudioBookId_invalid() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        List<Genre> mockGenres = new ArrayList<>();
        mockGenres.add(new Genre("Mock Genre 1"));
        mockGenres.add(new Genre("Mock Genre 2"));
        FindAllGenreQuery expectedQuery = new FindAllGenreQuery(Optional.of(audioBookId));

        when(findAllGenreQueryHandler.handle(expectedQuery)).thenReturn(mockGenres);

        mockMvc.perform(get("/api/v1/genre").param("audioBookId", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testGetAllGenre_success_noGenres() throws Exception {
        FindAllGenreQuery expectedQuery = new FindAllGenreQuery(Optional.empty());

        when(findAllGenreQueryHandler.handle(expectedQuery)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testDeleteGenre_success() throws Exception {
        Genre mockGenre = new Genre("Mock Genre 1");

        when(deleteGenreCommandHandler.handle(any(DeleteGenreCommand.class))).thenReturn(mockGenre);

        mockMvc.perform(delete("/api/v1/genre/" + mockGenre.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockGenre.getId().toString())))
                .andExpect(jsonPath("$.name", is(mockGenre.getName())));
    }

    @Test
    public void testDeleteGenre_noContent() throws Exception {
        Genre mockGenre = new Genre("Mock Genre 1");

        when(deleteGenreCommandHandler.handle(any(DeleteGenreCommand.class))).thenThrow(new GenreNotFoundException(mockGenre.getId()));

        mockMvc.perform(delete("/api/v1/genre/" + mockGenre.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testCreateGenre_success() throws Exception {
        Genre mockGenre = new Genre("Mock Genre 1");
        CreateGenreRequest request = new CreateGenreRequest(mockGenre.getName());
        CreateGenreCommand expectedCommand = new CreateGenreCommand(request.getName());

        when(createGenreCommandHandler.handle(expectedCommand)).thenReturn(mockGenre);

        mockMvc.perform(post("/api/v1/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockGenre.getId().toString())))
                .andExpect(jsonPath("$.name", is(mockGenre.getName())));
    }
}
