package xyz.f3l1x.open_audio_books_api.app.genre;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/genre")
public class GenreController {
    private final ModelMapper mapper;

    public GenreController(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
