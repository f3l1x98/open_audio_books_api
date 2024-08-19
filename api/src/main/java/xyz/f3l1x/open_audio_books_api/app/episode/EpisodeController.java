package xyz.f3l1x.open_audio_books_api.app.episode;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.f3l1x.core.audio_book.command.add_new_episode.AddNewEpisodeCommand;
import xyz.f3l1x.core.audio_book.exception.AudioBookNotFoundException;
import xyz.f3l1x.core.audio_book.exception.UniqueEpisodeViolationException;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommand;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommand;
import xyz.f3l1x.core.episode.command.upload.UploadCommand;
import xyz.f3l1x.core.episode.exception.EpisodeNotFoundException;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQuery;
import xyz.f3l1x.core.episode.query.get_file.GetFileQuery;
import xyz.f3l1x.core.episode.query.get_file.GetFileQueryResult;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.CreateEpisodeRequest;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.EpisodeDto;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.UpdateEpisodeRequest;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/audio-book/{audioBookId}/episode")
@Slf4j
public class EpisodeController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler;
    private final ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler;
    private final ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler;
    private final ICommandHandler<AddNewEpisodeCommand, Episode> addNewEpisodeCommandHandler;
    private final IQueryHandler<GetFileQuery, GetFileQueryResult> getFileQueryHandler;
    private final ICommandHandler<UploadCommand, Episode> uploadCommandHandler;

    public EpisodeController(
            ModelMapper mapper,
            IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler,
            ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler,
            ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler,
            ICommandHandler<AddNewEpisodeCommand, Episode> addNewEpisodeCommandHandler,
            IQueryHandler<GetFileQuery, GetFileQueryResult> getFileQueryHandler,
            ICommandHandler<UploadCommand, Episode> uploadCommandHandler
    ) {
        this.mapper = mapper;
        this.findAllForAudioBookEpisodeQueryHandler = findAllForAudioBookEpisodeQueryHandler;
        this.deleteEpisodeCommandHandler = deleteEpisodeCommandHandler;
        this.updateEpisodeCommandHandler = updateEpisodeCommandHandler;
        this.addNewEpisodeCommandHandler = addNewEpisodeCommandHandler;
        this.getFileQueryHandler = getFileQueryHandler;
        this.uploadCommandHandler = uploadCommandHandler;
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
    public ResponseEntity<EpisodeDto> createEpisode(@PathVariable UUID audioBookId, @Valid @RequestBody CreateEpisodeRequest body) {
        AddNewEpisodeCommand command = new AddNewEpisodeCommand(
                audioBookId,
                body.getNumber(),
                body.getTitle(),
                body.getSummary(),
                body.getReleaseDate(),
                body.getDuration());

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

    @GetMapping("/{episodeId}/stream")
    public ResponseEntity<Object> streamEpisode(@PathVariable("episodeId") UUID episodeId) {

        try {
            GetFileQuery query = new GetFileQuery(episodeId);
            GetFileQueryResult result = getFileQueryHandler.handle(query);

            InputStreamResource inputStreamResource = new InputStreamResource(result.stream());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(result.contentLength());
            headers.set(HttpHeaders.CONTENT_TYPE, result.mimeType());
            return ResponseEntity.ok().headers(headers).body(inputStreamResource);
        } catch (EpisodeNotFoundException | FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(Arrays.deepToString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{episodeId}/upload")
    public ResponseEntity uploadEpisode(@PathVariable UUID episodeId, @RequestParam("file") MultipartFile file) {
        try {
            UploadCommand command = new UploadCommand(episodeId, file.getInputStream(), file.getContentType());
            Episode result = uploadCommandHandler.handle(command);

            return ResponseEntity.ok("Success");
        } catch (EpisodeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(Arrays.deepToString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
