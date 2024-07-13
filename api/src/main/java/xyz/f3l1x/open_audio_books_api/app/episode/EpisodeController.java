package xyz.f3l1x.open_audio_books_api.app.episode;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQuery;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.EpisodeDto;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/audio-book/{audioBookId}/episode")
public class EpisodeController {
    private final ModelMapper mapper;
    private final IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler;

    public EpisodeController(ModelMapper mapper, IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler) {
        this.mapper = mapper;
        this.findAllForAudioBookEpisodeQueryHandler = findAllForAudioBookEpisodeQueryHandler;
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
}
