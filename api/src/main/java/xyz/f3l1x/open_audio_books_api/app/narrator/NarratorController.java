package xyz.f3l1x.open_audio_books_api.app.narrator;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.narrator.command.create.CreateNarratorCommand;
import xyz.f3l1x.core.narrator.command.delete.DeleteNarratorCommand;
import xyz.f3l1x.core.narrator.command.update.UpdateNarratorCommand;
import xyz.f3l1x.core.narrator.exception.NarratorNotFoundException;
import xyz.f3l1x.core.narrator.query.find_all.FindAllNarratorQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.narrator.dto.CreateNarratorRequest;
import xyz.f3l1x.open_audio_books_api.app.narrator.dto.NarratorDto;
import xyz.f3l1x.open_audio_books_api.app.narrator.dto.NarratorListDto;
import xyz.f3l1x.open_audio_books_api.app.narrator.dto.UpdateNarratorRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/narrator")
public class NarratorController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllNarratorQuery, List<Narrator>> findAllNarratorQueryHandler;
    private final ICommandHandler<CreateNarratorCommand, Narrator> createNarratorCommandHandler;
    private final ICommandHandler<UpdateNarratorCommand, Narrator> updateNarratorCommandHandler;
    private final ICommandHandler<DeleteNarratorCommand, Narrator> deleteNarratorCommandHandler;

    public NarratorController(
            ModelMapper mapper,
            IQueryHandler<FindAllNarratorQuery, List<Narrator>> findAllNarratorQueryHandler,
            ICommandHandler<CreateNarratorCommand, Narrator> createNarratorCommandHandler,
            ICommandHandler<UpdateNarratorCommand, Narrator> updateNarratorCommandHandler,
            ICommandHandler<DeleteNarratorCommand, Narrator> deleteNarratorCommandHandler
    ) {
        this.mapper = mapper;
        this.findAllNarratorQueryHandler = findAllNarratorQueryHandler;
        this.createNarratorCommandHandler = createNarratorCommandHandler;
        this.updateNarratorCommandHandler = updateNarratorCommandHandler;
        this.deleteNarratorCommandHandler = deleteNarratorCommandHandler;
    }

    @GetMapping("")
    public ResponseEntity<List<NarratorListDto>> getAllNarrators(@RequestParam("audioBookId") Optional<UUID> audioBookId) {
        FindAllNarratorQuery query = new FindAllNarratorQuery(audioBookId);

        List<Narrator> result;
        try {
            result = findAllNarratorQueryHandler.handle(query);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, new TypeToken<List<NarratorListDto>>() {}.getType()));
    }

    @PostMapping("")
    public ResponseEntity<NarratorDto> createNarrator(@RequestBody CreateNarratorRequest body) {
        CreateNarratorCommand command = new CreateNarratorCommand(body.getFirstName(), body.getMiddleName(), body.getLastName());

        Narrator result;
        try {
            result = createNarratorCommandHandler.handle(command);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, NarratorDto.class));
    }

    @PutMapping("/{narratorId}")
    public ResponseEntity<NarratorDto> updateNarrator(@PathVariable UUID narratorId, @RequestBody UpdateNarratorRequest body) {
        UpdateNarratorCommand command = new UpdateNarratorCommand(narratorId, body.getFirstName(), body.getMiddleName(), body.getLastName());

        Narrator result;
        try {
            result = updateNarratorCommandHandler.handle(command);
        } catch (NarratorNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, NarratorDto.class));
    }

    @DeleteMapping("/{narratorId}")
    public ResponseEntity<NarratorDto> deleteNarrator(@PathVariable UUID narratorId) {
        DeleteNarratorCommand command = new DeleteNarratorCommand(narratorId);

        Narrator result;
        try {
            result = deleteNarratorCommandHandler.handle(command);
        } catch (NarratorNotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(this.mapper.map(result, NarratorDto.class));
    }
}
