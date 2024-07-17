package xyz.f3l1x.open_audio_books_api.app.episode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.f3l1x.core.audio_book.command.add_new_episode.AddNewEpisodeCommand;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.audio_book.exception.UniqueEpisodeViolationException;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommand;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommand;
import xyz.f3l1x.core.episode.exception.EpisodeNotFoundException;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.CreateEpisodeRequest;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.UpdateEpisodeRequest;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EpisodeController.class)
@Import(ModelMapper.class)
public class EpisodeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler;
    @MockBean
    private ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler;
    @MockBean
    private ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler;
    @MockBean
    private ICommandHandler<AddNewEpisodeCommand, Episode> addNewEpisodeCommandHandler;

    @Test
    public void testGetAllEpisodesForAudioBook_success() throws Exception {
        List<Episode> mockEpisodes = new ArrayList<>();
        mockEpisodes.add(new Episode(0, "Episode 0", null, new Date(), null));
        mockEpisodes.add(new Episode(1, "Episode 1", null, new Date(), null));
        FindAllForAudioBookEpisodeQuery expectedQuery = new FindAllForAudioBookEpisodeQuery(UUID.randomUUID());

        when(findAllForAudioBookEpisodeQueryHandler.handle(expectedQuery)).thenReturn(mockEpisodes);

        mockMvc.perform(get("/api/v1/audio-book/" + expectedQuery.audioBookId() + "/episode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllEpisodesForAudioBook_success_noEpisodes() throws Exception {
        FindAllForAudioBookEpisodeQuery expectedQuery = new FindAllForAudioBookEpisodeQuery(UUID.randomUUID());

        when(findAllForAudioBookEpisodeQueryHandler.handle(expectedQuery)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/audio-book/" + expectedQuery.audioBookId() + "/episode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testDeleteEpisode_success() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        Episode mockEpisode = new Episode(0, "Episode 0", null, new Date(), null);

        when(deleteEpisodeCommandHandler.handle(any(DeleteEpisodeCommand.class))).thenReturn(mockEpisode);

        mockMvc.perform(delete("/api/v1/audio-book/" + audioBookId + "/episode/" + mockEpisode.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockEpisode.getId().toString())))
                .andExpect(jsonPath("$.title", is(mockEpisode.getTitle())));
    }

    @Test
    public void testDeleteEpisode_noContent() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        Episode mockEpisode = new Episode(0, "Episode 0", null, new Date(), null);

        when(deleteEpisodeCommandHandler.handle(any(DeleteEpisodeCommand.class))).thenThrow(new EpisodeNotFoundException(mockEpisode.getId()));

        mockMvc.perform(delete("/api/v1/audio-book/" + audioBookId + "/episode/" + mockEpisode.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testCreateEpisode_success() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        Episode mockEpisode = new Episode(0, "Episode 0", null, new Date(), null);
        CreateEpisodeRequest request = new CreateEpisodeRequest(
                mockEpisode.getNumber(),
                mockEpisode.getTitle(),
                mockEpisode.getSummary(),
                mockEpisode.getReleaseDate());
        AddNewEpisodeCommand expectedCommand = new AddNewEpisodeCommand(
                audioBookId,
                request.getNumber(),
                request.getTitle(),
                request.getSummary(),
                request.getReleaseDate());

        when(addNewEpisodeCommandHandler.handle(expectedCommand)).thenReturn(mockEpisode);

        mockMvc.perform(post("/api/v1/audio-book/" + audioBookId + "/episode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockEpisode.getId().toString())))
                .andExpect(jsonPath("$.title", is(mockEpisode.getTitle())));
    }

    @Test
    public void testCreateEpisode_audioBookNotFound() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        Episode mockEpisode = new Episode(0, "Episode 0", null, new Date(), null);
        CreateEpisodeRequest request = new CreateEpisodeRequest(
                mockEpisode.getNumber(),
                mockEpisode.getTitle(),
                mockEpisode.getSummary(),
                mockEpisode.getReleaseDate());
        AddNewEpisodeCommand expectedCommand = new AddNewEpisodeCommand(
                audioBookId,
                request.getNumber(),
                request.getTitle(),
                request.getSummary(),
                request.getReleaseDate());

        when(addNewEpisodeCommandHandler.handle(expectedCommand)).thenThrow(new AudioBookNotFoundException(audioBookId));

        mockMvc.perform(post("/api/v1/audio-book/" + audioBookId + "/episode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testCreateEpisode_uniqueEpisodeViolationException() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        Episode mockEpisode = new Episode(0, "Episode 0", null, new Date(), null);
        CreateEpisodeRequest request = new CreateEpisodeRequest(
                mockEpisode.getNumber(),
                mockEpisode.getTitle(),
                mockEpisode.getSummary(),
                mockEpisode.getReleaseDate());
        AddNewEpisodeCommand expectedCommand = new AddNewEpisodeCommand(
                audioBookId,
                request.getNumber(),
                request.getTitle(),
                request.getSummary(),
                request.getReleaseDate());

        when(addNewEpisodeCommandHandler.handle(expectedCommand)).thenThrow(new UniqueEpisodeViolationException(true, true));

        mockMvc.perform(post("/api/v1/audio-book/" + audioBookId + "/episode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testUpdateEpisode_success() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        Episode mockEpisode = new Episode(0, "Episode 0", null, new Date(), null);
        UpdateEpisodeRequest request = new UpdateEpisodeRequest(
                mockEpisode.getNumber(),
                mockEpisode.getTitle(),
                mockEpisode.getSummary(),
                mockEpisode.getReleaseDate());
        UpdateEpisodeCommand expectedCommand = new UpdateEpisodeCommand(
                mockEpisode.getId(),
                request.getNumber(),
                request.getTitle(),
                request.getSummary(),
                request.getReleaseDate());

        when(updateEpisodeCommandHandler.handle(expectedCommand)).thenReturn(mockEpisode);

        mockMvc.perform(put("/api/v1/audio-book/" + audioBookId + "/episode/" + mockEpisode.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockEpisode.getId().toString())))
                .andExpect(jsonPath("$.title", is(mockEpisode.getTitle())));
    }

    @Test
    public void testUpdateEpisode_notFound() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        Episode mockEpisode = new Episode(0, "Episode 0", null, new Date(), null);
        UpdateEpisodeRequest request = new UpdateEpisodeRequest(
                mockEpisode.getNumber(),
                mockEpisode.getTitle(),
                mockEpisode.getSummary(),
                mockEpisode.getReleaseDate());
        UpdateEpisodeCommand expectedCommand = new UpdateEpisodeCommand(
                mockEpisode.getId(),
                request.getNumber(),
                request.getTitle(),
                request.getSummary(),
                request.getReleaseDate());

        when(updateEpisodeCommandHandler.handle(expectedCommand)).thenThrow(new EpisodeNotFoundException(mockEpisode.getId()));

        mockMvc.perform(put("/api/v1/audio-book/" + audioBookId + "/episode/" + mockEpisode.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
