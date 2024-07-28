package xyz.f3l1x.open_audio_books_api.app.episode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEpisodeRequest {
    @JsonProperty()
    private Integer number;
    @JsonProperty()
    private String title;
    @JsonProperty()
    private String summary;
    @JsonProperty()
    private Date releaseDate;
    @JsonProperty()
    @Min(value = 1, message = "Duration must be greater than zero")
    private Long duration;
}
