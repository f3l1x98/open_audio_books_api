package xyz.f3l1x.open_audio_books_api.app.audio_book;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.AudioBookNotFoundException;
import xyz.f3l1x.core.audio_book.command.create.CreateAudioBookCommand;
import xyz.f3l1x.core.audio_book.command.update.UpdateAudioBookCommand;
import xyz.f3l1x.core.audio_book.query.find_all.FindAllAudioBookQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.AudioBookDto;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.CreateAudioBookRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController()
@RequestMapping("/api/v1/audio-book")
public class AudioBookController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler;
    private final ICommandHandler<CreateAudioBookCommand, AudioBook> createAudioBookCommandHandler;
    private final ICommandHandler<UpdateAudioBookCommand, AudioBook> updateAudioBookCommandHandler;

    public AudioBookController(
            ModelMapper mapper,
            IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler,
            ICommandHandler<CreateAudioBookCommand, AudioBook> createAudioBookCommandHandler,
            ICommandHandler<UpdateAudioBookCommand, AudioBook> updateAudioBookCommandHandler
    ) {
        this.mapper = mapper;
        this.findAllAudioBookQueryHandler = findAllAudioBookQueryHandler;
        this.createAudioBookCommandHandler = createAudioBookCommandHandler;
        this.updateAudioBookCommandHandler = updateAudioBookCommandHandler;
    }

    @GetMapping("")
    public ResponseEntity<List<AudioBookDto>> getAllAudioBooks() {
        FindAllAudioBookQuery query = new FindAllAudioBookQuery(null, null);

        List<AudioBook> result;
        try {
            result = findAllAudioBookQueryHandler.handle(query);
        } catch (Exception e) {
            // TODO handle with corresponding response code
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, new TypeToken<List<AudioBookDto>>() {}.getType()));
    }

    @PostMapping()
    public ResponseEntity<AudioBookDto> createAudioBook(@RequestBody CreateAudioBookRequest body) {
        CreateAudioBookCommand command = new CreateAudioBookCommand(
                body.getTitle(),
                body.getSummary(),
                body.getReleaseDate(),
                body.getOngoing(),
                body.getRating(),
                body.getGenreIds());

        AudioBook audioBook;
        try {
            audioBook = createAudioBookCommandHandler.handle(command);
        } catch (Exception e) {
            // TODO handle with corresponding response code
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(audioBook, AudioBookDto.class));
    }

    // TODO SLUG https://spring.io/guides/tutorials/spring-boot-kotlin
    @PostMapping("/{id}")
    public ResponseEntity<AudioBookDto> createAudioBook(@PathVariable Long id) {
        UpdateAudioBookCommand command = new UpdateAudioBookCommand(
                id,
                null,
                null,
                null,
                null,
                null,
                new ArrayList<>(),
                new HashSet<>());

        AudioBook audioBook;
        try {
            audioBook = updateAudioBookCommandHandler.handle(command);
        } catch (AudioBookNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // TODO handle with corresponding response code
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(audioBook, AudioBookDto.class));
    }
}
