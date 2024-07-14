package xyz.f3l1x.open_audio_books_api.app.genre;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.genre.command.create.CreateGenreCommand;
import xyz.f3l1x.core.genre.command.delete.DeleteGenreCommand;
import xyz.f3l1x.core.genre.exception.GenreNotFoundException;
import xyz.f3l1x.core.genre.query.find_all.FindAllGenreQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.genre.dto.CreateGenreRequest;
import xyz.f3l1x.open_audio_books_api.app.genre.dto.GenreDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/genre")
public class GenreController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllGenreQuery, List<Genre>> findAllGenreQueryHandler;
    private final ICommandHandler<CreateGenreCommand, Genre> createGenreCommandHandler;
    private final ICommandHandler<DeleteGenreCommand, Genre> deleteGenreCommandHandler;

    public GenreController(
            ModelMapper mapper,
            IQueryHandler<FindAllGenreQuery, List<Genre>> findAllGenreQueryHandler,
            ICommandHandler<CreateGenreCommand, Genre> createGenreCommandHandler,
            ICommandHandler<DeleteGenreCommand, Genre> deleteGenreCommandHandler
    ) {
        this.mapper = mapper;
        this.findAllGenreQueryHandler = findAllGenreQueryHandler;
        this.createGenreCommandHandler = createGenreCommandHandler;
        this.deleteGenreCommandHandler = deleteGenreCommandHandler;
    }

    @GetMapping("")
    public ResponseEntity<List<GenreDto>> getAllGenres(@RequestParam("audioBookId") Optional<UUID> audioBookId) {
        FindAllGenreQuery query = new FindAllGenreQuery(audioBookId);

        List<Genre> result;
        try {
            result = findAllGenreQueryHandler.handle(query);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, new TypeToken<List<GenreDto>>() {}.getType()));
    }

    @PostMapping("")
    public ResponseEntity<GenreDto> createGenre(@RequestBody CreateGenreRequest body) {
        CreateGenreCommand command = new CreateGenreCommand(body.getName());

        Genre result;
        try {
            result = createGenreCommandHandler.handle(command);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, GenreDto.class));
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<GenreDto> deleteGenre(@PathVariable UUID genreId) {
        DeleteGenreCommand command = new DeleteGenreCommand(genreId);

        Genre result;
        try {
            result = deleteGenreCommandHandler.handle(command);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, GenreDto.class));
    }
}
