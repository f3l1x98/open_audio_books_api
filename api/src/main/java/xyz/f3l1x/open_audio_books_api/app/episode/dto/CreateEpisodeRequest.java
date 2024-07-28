package xyz.f3l1x.open_audio_books_api.app.episode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long duration;
}
