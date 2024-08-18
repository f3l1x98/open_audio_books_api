package xyz.f3l1x.open_audio_books_api.app.audio_book;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.command.delete.DeleteAudioBookCommand;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.audio_book.command.create.CreateAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.update.UpdateAudioBookCommand;
import xyz.f3l1x.core.audio_book.query.find_all.FindAllAudioBookQuery;
import xyz.f3l1x.core.audio_book.query.find_by_id.FindAudioBookByIdQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.AudioBookDto;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.AudioBookListDto;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.CreateAudioBookRequest;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.UpdateAudioBookRequest;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/audio-book")
public class AudioBookController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler;
    private final IQueryHandler<FindAudioBookByIdQuery, AudioBook> findAudioBookByIdQueryHandler;
    private final ICommandHandler<CreateAudioBookCommand, AudioBook> createAudioBookCommandHandler;
    private final ICommandHandler<UpdateAudioBookCommand, AudioBook> updateAudioBookCommandHandler;
    private final ICommandHandler<DeleteAudioBookCommand, AudioBook> deleteAudioBookCommandHandler;

    public AudioBookController(
            ModelMapper mapper,
            IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler,
            IQueryHandler<FindAudioBookByIdQuery, AudioBook> findAudioBookByIdQueryHandler,
            ICommandHandler<CreateAudioBookCommand, AudioBook> createAudioBookCommandHandler,
            ICommandHandler<UpdateAudioBookCommand, AudioBook> updateAudioBookCommandHandler,
            ICommandHandler<DeleteAudioBookCommand, AudioBook> deleteAudioBookCommandHandler
    ) {
        this.mapper = mapper;
        this.findAllAudioBookQueryHandler = findAllAudioBookQueryHandler;
        this.findAudioBookByIdQueryHandler = findAudioBookByIdQueryHandler;
        this.createAudioBookCommandHandler = createAudioBookCommandHandler;
        this.updateAudioBookCommandHandler = updateAudioBookCommandHandler;
        this.deleteAudioBookCommandHandler = deleteAudioBookCommandHandler;
    }


    // TODO pagination
    @GetMapping("")
    public ResponseEntity<List<AudioBookListDto>> getAllAudioBooks() {
        FindAllAudioBookQuery query = new FindAllAudioBookQuery(null, null);

        List<AudioBook> result;
        try {
            result = findAllAudioBookQueryHandler.handle(query);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, new TypeToken<List<AudioBookListDto>>() {}.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AudioBookDto> getAudioBookById(@PathVariable("id") UUID id) {
        FindAudioBookByIdQuery query = new FindAudioBookByIdQuery(id);

        AudioBook result;
        try {
            result = findAudioBookByIdQueryHandler.handle(query);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, AudioBookDto.class));
    }

    @PostMapping()
    public ResponseEntity<AudioBookDto> createAudioBook(@Valid @RequestBody CreateAudioBookRequest body) {
        CreateAudioBookCommand command = new CreateAudioBookCommand(
                body.getTitle(),
                body.getSummary(),
                body.getReleaseDate(),
                body.getOngoing(),
                body.getRating(),
                body.getGenreIds(),
                body.getAuthorIds(),
                body.getNarratorIds());

        AudioBook audioBook;
        try {
            audioBook = createAudioBookCommandHandler.handle(command);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(audioBook, AudioBookDto.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AudioBookDto> updateAudioBook(@PathVariable UUID id, @RequestBody UpdateAudioBookRequest body) {
        UpdateAudioBookCommand command = new UpdateAudioBookCommand(
                id,
                body.getTitle(),
                body.getSummary(),
                body.getReleaseDate(),
                body.getOngoing(),
                body.getRating());

        AudioBook audioBook;
        try {
            audioBook = updateAudioBookCommandHandler.handle(command);
        } catch (AudioBookNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(audioBook, AudioBookDto.class));
    }

    @DeleteMapping("/{audioBookId}")
    public ResponseEntity<AudioBookDto> deleteAudioBook(@PathVariable UUID audioBookId) {
        DeleteAudioBookCommand command = new DeleteAudioBookCommand(audioBookId);

        AudioBook audioBook;
        try {
            audioBook = deleteAudioBookCommandHandler.handle(command);
        } catch (AudioBookNotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(audioBook, AudioBookDto.class));
    }
}
