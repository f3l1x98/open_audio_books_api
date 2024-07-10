package xyz.f3l1x.open_audio_books_api.app.episode;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/audio-book/{audioBookId}/episode")
public class EpisodeController {
    private final ModelMapper mapper;

    public EpisodeController(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
