package xyz.f3l1x.open_audio_books_api.app.genre.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GenreDto {
    private UUID id;
    private String name;
}
