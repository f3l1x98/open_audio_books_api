package xyz.f3l1x.open_audio_books_api.app.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.command.create.CreateAuthorCommand;
import xyz.f3l1x.core.author.command.delete.DeleteAuthorCommand;
import xyz.f3l1x.core.author.command.update.UpdateAuthorCommand;
import xyz.f3l1x.core.author.exception.AuthorNotFoundException;
import xyz.f3l1x.core.author.query.find_all.FindAllAuthorQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.author.dto.CreateAuthorRequest;
import xyz.f3l1x.open_audio_books_api.app.author.dto.UpdateAuthorRequest;

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

@WebMvcTest(AuthorController.class)
@Import(ModelMapper.class)
public class AuthorControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private IQueryHandler<FindAllAuthorQuery, List<Author>> findAllAuthorQueryHandler;
    @MockBean
    private ICommandHandler<CreateAuthorCommand, Author> createAuthorCommandHandler;
    @MockBean
    private ICommandHandler<UpdateAuthorCommand, Author> updateAuthorCommandHandler;
    @MockBean
    private ICommandHandler<DeleteAuthorCommand, Author> deleteAuthorCommandHandler;

    @Test
    public void testGetAllAuthors_success() throws Exception {
        List<Author> mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author("Mock", "Author", "1", new ArrayList<>()));
        mockAuthors.add(new Author("Mock", "Author", "2", new ArrayList<>()));
        FindAllAuthorQuery expectedQuery = new FindAllAuthorQuery(Optional.empty());

        when(findAllAuthorQueryHandler.handle(expectedQuery)).thenReturn(mockAuthors);

        mockMvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllAuthors_byAudioBookId_success() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        List<Author> mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author("Mock", "Author", "1", new ArrayList<>()));
        mockAuthors.add(new Author("Mock", "Author", "2", new ArrayList<>()));
        FindAllAuthorQuery expectedQuery = new FindAllAuthorQuery(Optional.of(audioBookId));

        when(findAllAuthorQueryHandler.handle(expectedQuery)).thenReturn(mockAuthors);

        mockMvc.perform(get("/api/v1/author").param("audioBookId", audioBookId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllAuthors_byAudioBookId_invalid() throws Exception {
        UUID audioBookId = UUID.randomUUID();
        List<Author> mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author("Mock", "Author", "1", new ArrayList<>()));
        mockAuthors.add(new Author("Mock", "Author", "2", new ArrayList<>()));
        FindAllAuthorQuery expectedQuery = new FindAllAuthorQuery(Optional.of(audioBookId));

        when(findAllAuthorQueryHandler.handle(expectedQuery)).thenReturn(mockAuthors);

        mockMvc.perform(get("/api/v1/author").param("audioBookId", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("Failed to convert 'audioBookId' with value: 'invalid'")));
    }

    @Test
    public void testGetAllAuthor_success_noAuthors() throws Exception {
        FindAllAuthorQuery expectedQuery = new FindAllAuthorQuery(Optional.empty());

        when(findAllAuthorQueryHandler.handle(expectedQuery)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testCreateAuthor_success() throws Exception {
        Author mockAuthor = new Author("Mock", "Author", "1", new ArrayList<>());
        CreateAuthorRequest request = new CreateAuthorRequest(mockAuthor.getFirstName(), mockAuthor.getMiddleName(), mockAuthor.getLastName());
        CreateAuthorCommand expectedCommand = new CreateAuthorCommand(request.getFirstName(), request.getMiddleName(), request.getLastName());

        when(createAuthorCommandHandler.handle(expectedCommand)).thenReturn(mockAuthor);

        mockMvc.perform(post("/api/v1/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockAuthor.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(mockAuthor.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(mockAuthor.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(mockAuthor.getLastName())));
    }

    @Test
    public void testUpdateAuthor_success() throws Exception {
        Author mockAuthor = new Author("Mock", "Author", "1", new ArrayList<>());
        UpdateAuthorRequest request = new UpdateAuthorRequest(mockAuthor.getFirstName(), mockAuthor.getMiddleName(), mockAuthor.getLastName());
        UpdateAuthorCommand expectedCommand = new UpdateAuthorCommand(mockAuthor.getId(), request.getFirstName(), request.getMiddleName(), request.getLastName());

        when(updateAuthorCommandHandler.handle(expectedCommand)).thenReturn(mockAuthor);

        mockMvc.perform(put("/api/v1/author/" + mockAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockAuthor.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(mockAuthor.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(mockAuthor.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(mockAuthor.getLastName())));
    }

    @Test
    public void testUpdateAuthor_notFound() throws Exception {
        Author mockAuthor = new Author("Mock", "Author", "1", new ArrayList<>());
        UpdateAuthorRequest request = new UpdateAuthorRequest(mockAuthor.getFirstName(), mockAuthor.getMiddleName(), mockAuthor.getLastName());
        UpdateAuthorCommand expectedCommand = new UpdateAuthorCommand(mockAuthor.getId(), request.getFirstName(), request.getMiddleName(), request.getLastName());

        when(updateAuthorCommandHandler.handle(expectedCommand)).thenThrow(new AuthorNotFoundException(mockAuthor.getId()));

        mockMvc.perform(put("/api/v1/author/" + mockAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testDeleteAuthor_success() throws Exception {
        Author mockAuthor = new Author("Mock", "Author", "1", new ArrayList<>());

        when(deleteAuthorCommandHandler.handle(any(DeleteAuthorCommand.class))).thenReturn(mockAuthor);

        mockMvc.perform(delete("/api/v1/author/" + mockAuthor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockAuthor.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(mockAuthor.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(mockAuthor.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(mockAuthor.getLastName())));
    }

    @Test
    public void testDeleteAuthor_noContent() throws Exception {
        Author mockAuthor = new Author("Mock", "Author", "1", new ArrayList<>());

        when(deleteAuthorCommandHandler.handle(any(DeleteAuthorCommand.class))).thenThrow(new AuthorNotFoundException(mockAuthor.getId()));

        mockMvc.perform(delete("/api/v1/author/" + mockAuthor.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
