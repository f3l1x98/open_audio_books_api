package xyz.f3l1x.open_audio_books_api.app.genre.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGenreRequest {
    @JsonProperty()
    private String name;
}
