package xyz.f3l1x.open_audio_books_api.app.audio_book;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@WebMvcTest(AudioBookController.class)
@Import(ModelMapper.class)
public class AudioBookControllerTests {
    @Autowired
    private MockMvc mockMvc;

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
}
