package xyz.f3l1x.open_audio_books_api.app.episode;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommand;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommand;
import xyz.f3l1x.core.episode.exception.EpisodeNotFoundException;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQuery;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.EpisodeDto;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.UpdateEpisodeRequest;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/audio-book/{audioBookId}/episode")
public class EpisodeController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler;
    private final ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler;
    private final ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler;

    public EpisodeController(
            ModelMapper mapper,
            IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler,
            ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler,
            ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler
    ) {
        this.mapper = mapper;
        this.findAllForAudioBookEpisodeQueryHandler = findAllForAudioBookEpisodeQueryHandler;
        this.deleteEpisodeCommandHandler = deleteEpisodeCommandHandler;
        this.updateEpisodeCommandHandler = updateEpisodeCommandHandler;
    }

    @GetMapping("")
    public ResponseEntity<List<EpisodeDto>> getAllEpisodesForAudioBook(@PathVariable Long audioBookId) {
        FindAllForAudioBookEpisodeQuery query = new FindAllForAudioBookEpisodeQuery(audioBookId);

        List<Episode> result;
        try {
            result = findAllForAudioBookEpisodeQueryHandler.handle(query);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(result, new TypeToken<List<EpisodeDto>>() {}.getType()));
    }

    @DeleteMapping("/{episodeId}")
    public ResponseEntity<EpisodeDto> deleteEpisode(@PathVariable Long episodeId) {
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
    public ResponseEntity<EpisodeDto> updateEpisode(@PathVariable Long episodeId, @RequestBody UpdateEpisodeRequest body) {
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
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(mapper.map(result, EpisodeDto.class));
    }
}
