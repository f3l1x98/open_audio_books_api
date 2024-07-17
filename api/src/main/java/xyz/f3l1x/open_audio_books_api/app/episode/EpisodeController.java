package xyz.f3l1x.open_audio_books_api.app.episode;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
import xyz.f3l1x.open_audio_books_api.app.episode.dto.EpisodeDto;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.UpdateEpisodeRequest;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/audio-book/{audioBookId}/episode")
public class EpisodeController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler;
    private final ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler;
    private final ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler;
    private final ICommandHandler<AddNewEpisodeCommand, Episode> addNewEpisodeCommandHandler;

    public EpisodeController(
            ModelMapper mapper,
            IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler,
            ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler,
            ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler,
            ICommandHandler<AddNewEpisodeCommand, Episode> addNewEpisodeCommandHandler
    ) {
        this.mapper = mapper;
        this.findAllForAudioBookEpisodeQueryHandler = findAllForAudioBookEpisodeQueryHandler;
        this.deleteEpisodeCommandHandler = deleteEpisodeCommandHandler;
        this.updateEpisodeCommandHandler = updateEpisodeCommandHandler;
        this.addNewEpisodeCommandHandler = addNewEpisodeCommandHandler;
    }

    @GetMapping("")
    public ResponseEntity<List<EpisodeDto>> getAllEpisodesForAudioBook(@PathVariable UUID audioBookId) {
        FindAllForAudioBookEpisodeQuery query = new FindAllForAudioBookEpisodeQuery(audioBookId);

        List<Episode> result;
        try {
            result = findAllForAudioBookEpisodeQueryHandler.handle(query);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(result, new TypeToken<List<EpisodeDto>>() {}.getType()));
    }

    @PostMapping("")
    public ResponseEntity<EpisodeDto> createEpisode(@PathVariable UUID audioBookId, @RequestBody CreateEpisodeRequest body) {
        AddNewEpisodeCommand command = new AddNewEpisodeCommand(
                audioBookId,
                body.getNumber(),
                body.getTitle(),
                body.getSummary(),
                body.getReleaseDate());

        Episode result;
        try {
            result = addNewEpisodeCommandHandler.handle(command);
        } catch (AudioBookNotFoundException | UniqueEpisodeViolationException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(result, EpisodeDto.class));
    }

    @DeleteMapping("/{episodeId}")
    public ResponseEntity<EpisodeDto> deleteEpisode(@PathVariable UUID episodeId) {
        DeleteEpisodeCommand command = new DeleteEpisodeCommand(episodeId);

        Episode result;
        try {
            result = deleteEpisodeCommandHandler.handle(command);
        } catch (EpisodeNotFoundException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(result, EpisodeDto.class));
    }

    @PutMapping("/{episodeId}")
    public ResponseEntity<EpisodeDto> updateEpisode(@PathVariable UUID episodeId, @RequestBody UpdateEpisodeRequest body) {
        UpdateEpisodeCommand command = new UpdateEpisodeCommand(
                episodeId,
                body.getNumber(),
                body.getTitle(),
                body.getSummary(),
                body.getReleaseDate());

        Episode result;
        try {
            result = updateEpisodeCommandHandler.handle(command);
        } catch (EpisodeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(result, EpisodeDto.class));
    }
}
