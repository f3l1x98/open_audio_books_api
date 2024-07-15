package xyz.f3l1x.open_audio_books_api.app.audio_book;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
import xyz.f3l1x.core.audio_book.command.update.UpdateAudioBookCommand;
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
}
