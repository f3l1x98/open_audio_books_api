package xyz.f3l1x.open_audio_books_api.app.author;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.author.command.create.CreateAuthorCommand;
import xyz.f3l1x.core.author.command.delete.DeleteAuthorCommand;
import xyz.f3l1x.core.author.command.update.UpdateAuthorCommand;
import xyz.f3l1x.core.author.exception.AuthorNotFoundException;
import xyz.f3l1x.core.author.query.find_all.FindAllAuthorQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.author.dto.AuthorDto;
import xyz.f3l1x.open_audio_books_api.app.author.dto.AuthorListDto;
import xyz.f3l1x.open_audio_books_api.app.author.dto.CreateAuthorRequest;
import xyz.f3l1x.open_audio_books_api.app.author.dto.UpdateAuthorRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/author")
public class AuthorController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllAuthorQuery, List<Author>> findAllAuthorQueryHandler;
    private final ICommandHandler<CreateAuthorCommand, Author> createAuthorCommandHandler;
    private final ICommandHandler<UpdateAuthorCommand, Author> updateAuthorCommandHandler;
    private final ICommandHandler<DeleteAuthorCommand, Author> deleteAuthorCommandHandler;

    public AuthorController(
            ModelMapper mapper,
            IQueryHandler<FindAllAuthorQuery, List<Author>> findAllAuthorQueryHandler,
            ICommandHandler<CreateAuthorCommand, Author> createAuthorCommandHandler,
            ICommandHandler<UpdateAuthorCommand, Author> updateAuthorCommandHandler,
            ICommandHandler<DeleteAuthorCommand, Author> deleteAuthorCommandHandler
    ) {
        this.mapper = mapper;
        this.findAllAuthorQueryHandler = findAllAuthorQueryHandler;
        this.createAuthorCommandHandler = createAuthorCommandHandler;
        this.updateAuthorCommandHandler = updateAuthorCommandHandler;
        this.deleteAuthorCommandHandler = deleteAuthorCommandHandler;
    }

    @GetMapping("")
    public ResponseEntity<List<AuthorListDto>> getAllAuthors(@RequestParam("audioBookId") Optional<UUID> audioBookId) {
        FindAllAuthorQuery query = new FindAllAuthorQuery(audioBookId);

        List<Author> result;
        try {
            result = findAllAuthorQueryHandler.handle(query);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, new TypeToken<List<AuthorListDto>>() {}.getType()));
    }

    @PostMapping("")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody CreateAuthorRequest body) {
        CreateAuthorCommand command = new CreateAuthorCommand(body.getFirstName(), body.getMiddleName(), body.getLastName());

        Author result;
        try {
            result = createAuthorCommandHandler.handle(command);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, AuthorDto.class));
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable UUID authorId, @RequestBody UpdateAuthorRequest body) {
        UpdateAuthorCommand command = new UpdateAuthorCommand(authorId, body.getFirstName(), body.getMiddleName(), body.getLastName());

        Author result;
        try {
            result = updateAuthorCommandHandler.handle(command);
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, AuthorDto.class));
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable UUID authorId) {
        DeleteAuthorCommand command = new DeleteAuthorCommand(authorId);

        Author result;
        try {
            result = deleteAuthorCommandHandler.handle(command);
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, AuthorDto.class));
    }
}
