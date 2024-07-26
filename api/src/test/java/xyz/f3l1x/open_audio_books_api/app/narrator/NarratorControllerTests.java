package xyz.f3l1x.open_audio_books_api.app.narrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.narrator.command.create.CreateNarratorCommand;
import xyz.f3l1x.core.narrator.command.delete.DeleteNarratorCommand;
import xyz.f3l1x.core.narrator.command.update.UpdateNarratorCommand;
import xyz.f3l1x.core.narrator.exception.NarratorNotFoundException;
import xyz.f3l1x.core.narrator.query.find_all.FindAllNarratorQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.narrator.dto.CreateNarratorRequest;
import xyz.f3l1x.open_audio_books_api.app.narrator.dto.UpdateNarratorRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NarratorController.class)
@Import(ModelMapper.class)
public class NarratorControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private IQueryHandler<FindAllNarratorQuery, List<Narrator>> findAllNarratorQueryHandler;
    @MockBean
    private ICommandHandler<CreateNarratorCommand, Narrator> createNarratorCommandHandler;
    @MockBean
    private ICommandHandler<UpdateNarratorCommand, Narrator> updateNarratorCommandHandler;
    @MockBean
    private ICommandHandler<DeleteNarratorCommand, Narrator> deleteNarratorCommandHandler;

    @Test
    public void testGetAllNarrators_success() throws Exception {
        List<Narrator> mockNarrators = new ArrayList<>();
        mockNarrators.add(new Narrator("Mock", "Narrator", "1", new ArrayList<>()));
        mockNarrators.add(new Narrator("Mock", "Narrator", "2", new ArrayList<>()));
        FindAllNarratorQuery expectedQuery = new FindAllNarratorQuery(Optional.empty());

        when(findAllNarratorQueryHandler.handle(expectedQuery)).thenReturn(mockNarrators);

        mockMvc.perform(get("/api/v1/narrator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllNarrators_byAudioBookId_success() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        List<Narrator> mockNarrators = new ArrayList<>();
        mockNarrators.add(new Narrator("Mock", "Narrator", "1", new ArrayList<>()));
        mockNarrators.add(new Narrator("Mock", "Narrator", "2", new ArrayList<>()));
        FindAllNarratorQuery expectedQuery = new FindAllNarratorQuery(Optional.of(audioBookId));

        when(findAllNarratorQueryHandler.handle(expectedQuery)).thenReturn(mockNarrators);

        mockMvc.perform(get("/api/v1/narrator").param("audioBookId", audioBookId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllNarrators_byAudioBookId_invalid() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        List<Narrator> mockNarrators = new ArrayList<>();
        mockNarrators.add(new Narrator("Mock", "Narrator", "1", new ArrayList<>()));
        mockNarrators.add(new Narrator("Mock", "Narrator", "2", new ArrayList<>()));
        FindAllNarratorQuery expectedQuery = new FindAllNarratorQuery(Optional.of(audioBookId));

        when(findAllNarratorQueryHandler.handle(expectedQuery)).thenReturn(mockNarrators);

        mockMvc.perform(get("/api/v1/narrator").param("audioBookId", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("Failed to convert 'audioBookId' with value: 'invalid'")));
    }

    @Test
    public void testGetAllNarrator_success_noNarrators() throws Exception {
        FindAllNarratorQuery expectedQuery = new FindAllNarratorQuery(Optional.empty());

        when(findAllNarratorQueryHandler.handle(expectedQuery)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/narrator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testCreateNarrator_success() throws Exception {
        Narrator mockNarrator = new Narrator("Mock", "Narrator", "1", new ArrayList<>());
        CreateNarratorRequest request = new CreateNarratorRequest(mockNarrator.getFirstName(), mockNarrator.getMiddleName(), mockNarrator.getLastName());
        CreateNarratorCommand expectedCommand = new CreateNarratorCommand(request.getFirstName(), request.getMiddleName(), request.getLastName());

        when(createNarratorCommandHandler.handle(expectedCommand)).thenReturn(mockNarrator);

        mockMvc.perform(post("/api/v1/narrator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockNarrator.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(mockNarrator.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(mockNarrator.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(mockNarrator.getLastName())));
    }

    @Test
    public void testUpdateNarrator_success() throws Exception {
        Narrator mockNarrator = new Narrator("Mock", "Narrator", "1", new ArrayList<>());
        UpdateNarratorRequest request = new UpdateNarratorRequest(mockNarrator.getFirstName(), mockNarrator.getMiddleName(), mockNarrator.getLastName());
        UpdateNarratorCommand expectedCommand = new UpdateNarratorCommand(mockNarrator.getId(), request.getFirstName(), request.getMiddleName(), request.getLastName());

        when(updateNarratorCommandHandler.handle(expectedCommand)).thenReturn(mockNarrator);

        mockMvc.perform(put("/api/v1/narrator/" + mockNarrator.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockNarrator.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(mockNarrator.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(mockNarrator.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(mockNarrator.getLastName())));
    }

    @Test
    public void testUpdateNarrator_notFound() throws Exception {
        Narrator mockNarrator = new Narrator("Mock", "Narrator", "1", new ArrayList<>());
        UpdateNarratorRequest request = new UpdateNarratorRequest(mockNarrator.getFirstName(), mockNarrator.getMiddleName(), mockNarrator.getLastName());
        UpdateNarratorCommand expectedCommand = new UpdateNarratorCommand(mockNarrator.getId(), request.getFirstName(), request.getMiddleName(), request.getLastName());

        when(updateNarratorCommandHandler.handle(expectedCommand)).thenThrow(new NarratorNotFoundException(mockNarrator.getId()));

        mockMvc.perform(put("/api/v1/narrator/" + mockNarrator.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testDeleteNarrator_success() throws Exception {
        Narrator mockNarrator = new Narrator("Mock", "Narrator", "1", new ArrayList<>());

        when(deleteNarratorCommandHandler.handle(any(DeleteNarratorCommand.class))).thenReturn(mockNarrator);

        mockMvc.perform(delete("/api/v1/narrator/" + mockNarrator.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockNarrator.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(mockNarrator.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(mockNarrator.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(mockNarrator.getLastName())));
    }

    @Test
    public void testDeleteNarrator_noContent() throws Exception {
        Narrator mockNarrator = new Narrator("Mock", "Narrator", "1", new ArrayList<>());

        when(deleteNarratorCommandHandler.handle(any(DeleteNarratorCommand.class))).thenThrow(new NarratorNotFoundException(mockNarrator.getId()));

        mockMvc.perform(delete("/api/v1/narrator/" + mockNarrator.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
