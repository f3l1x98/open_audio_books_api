package xyz.f3l1x.open_audio_books_api.app.audio_book;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.command.create.CreateAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.delete.DeleteAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.update.UpdateAudioBookCommand;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.audio_book.query.find_all.FindAllAudioBookQuery;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.CreateAudioBookRequest;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.UpdateAudioBookRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@WebMvcTest(AudioBookController.class)
@Import(ModelMapper.class)
public class AudioBookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler;
    @MockBean
    private ICommandHandler<CreateAudioBookCommand, AudioBook> createAudioBookCommandHandler;
    @MockBean
    private ICommandHandler<UpdateAudioBookCommand, AudioBook> updateAudioBookCommandHandler;
    @MockBean
    private ICommandHandler<DeleteAudioBookCommand, AudioBook> deleteAudioBookCommandHandler;

    @Test
    public void testGetAllAudioBooks_success() throws Exception {
        List<AudioBook> mockAudioBooks = new ArrayList<>();
        mockAudioBooks.add(new AudioBook(
                "Mock 1",
                "Mock 1",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>()));
        mockAudioBooks.add(new AudioBook(
                "Mock 2",
                "Mock 2",
                new Date(),
                true,
                0,
                List.of(new Episode(0, "Episode 0", null, new Date(), null)),
                new HashSet<>()));

        when(findAllAudioBookQueryHandler.handle(any(FindAllAudioBookQuery.class))).thenReturn(mockAudioBooks);

        mockMvc.perform(get("/api/v1/audio-book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllAudioBooks_success_noAudioBooks() throws Exception {

        when(findAllAudioBookQueryHandler.handle(any(FindAllAudioBookQuery.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/audio-book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testDeleteAudioBook_success() throws Exception {
        AudioBook mockAudioBook = new AudioBook(
                "Mock 1",
                "Mock 1",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>());

        when(deleteAudioBookCommandHandler.handle(any(DeleteAudioBookCommand.class))).thenReturn(mockAudioBook);

        mockMvc.perform(delete("/api/v1/audio-book/" + mockAudioBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockAudioBook.getId().toString())))
                .andExpect(jsonPath("$.title", is(mockAudioBook.getTitle())));
    }

    @Test
    public void testDeleteAudioBook_noContent() throws Exception {
        AudioBook mockAudioBook = new AudioBook(
                "Mock 1",
                "Mock 1",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>());

        when(deleteAudioBookCommandHandler.handle(any(DeleteAudioBookCommand.class))).thenThrow(new AudioBookNotFoundException(mockAudioBook.getId()));

        mockMvc.perform(delete("/api/v1/audio-book/" + mockAudioBook.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testCreateAudioBook_success() throws Exception {
        AudioBook mockAudioBook = new AudioBook(
                "Mock 1",
                "Mock 1",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>());
        CreateAudioBookRequest request = new CreateAudioBookRequest(
                mockAudioBook.getTitle(),
                mockAudioBook.getSummary(),
                mockAudioBook.getReleaseDate(),
                mockAudioBook.getOngoing(),
                mockAudioBook.getRating(),
                new ArrayList<>());
        CreateAudioBookCommand expectedCommand = new CreateAudioBookCommand(
                request.getTitle(),
                request.getSummary(),
                request.getReleaseDate(),
                request.getOngoing(),
                request.getRating(),
                request.getGenreIds());

        when(createAudioBookCommandHandler.handle(expectedCommand)).thenReturn(mockAudioBook);

        mockMvc.perform(post("/api/v1/audio-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockAudioBook.getId().toString())))
                .andExpect(jsonPath("$.title", is(mockAudioBook.getTitle())));
    }

    @Test
    public void testUpdateAudioBook_success() throws Exception {
        AudioBook mockAudioBook = new AudioBook(
                "Mock 1",
                "Mock 1",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>());
        UpdateAudioBookRequest request = new UpdateAudioBookRequest(
                mockAudioBook.getTitle(),
                mockAudioBook.getSummary(),
                mockAudioBook.getReleaseDate(),
                mockAudioBook.getOngoing(),
                mockAudioBook.getRating());
        UpdateAudioBookCommand expectedCommand = new UpdateAudioBookCommand(
                mockAudioBook.getId(),
                request.getTitle(),
                request.getSummary(),
                request.getReleaseDate(),
                request.getOngoing(),
                request.getRating());

        when(updateAudioBookCommandHandler.handle(expectedCommand)).thenReturn(mockAudioBook);

        mockMvc.perform(put("/api/v1/audio-book/" + mockAudioBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockAudioBook.getId().toString())))
                .andExpect(jsonPath("$.title", is(mockAudioBook.getTitle())));
    }

    @Test
    public void testUpdateAudioBook_notFound() throws Exception {
        AudioBook mockAudioBook = new AudioBook(
                "Mock 1",
                "Mock 1",
                new Date(),
                true,
                0,
                new ArrayList<>(),
                new HashSet<>());
        UpdateAudioBookRequest request = new UpdateAudioBookRequest(
                mockAudioBook.getTitle(),
                mockAudioBook.getSummary(),
                mockAudioBook.getReleaseDate(),
                mockAudioBook.getOngoing(),
                mockAudioBook.getRating());
        UpdateAudioBookCommand expectedCommand = new UpdateAudioBookCommand(
                mockAudioBook.getId(),
                request.getTitle(),
                request.getSummary(),
                request.getReleaseDate(),
                request.getOngoing(),
                request.getRating());

        when(updateAudioBookCommandHandler.handle(expectedCommand)).thenThrow(new AudioBookNotFoundException(mockAudioBook.getId()));

        mockMvc.perform(put("/api/v1/audio-book/" + mockAudioBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
