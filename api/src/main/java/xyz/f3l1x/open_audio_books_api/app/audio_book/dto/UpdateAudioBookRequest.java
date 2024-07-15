package xyz.f3l1x.open_audio_books_api.app.audio_book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAudioBookRequest {
    @JsonProperty()
    private String title;
    @JsonProperty()
    private String summary;
    @JsonProperty()
    private Date releaseDate;
    @JsonProperty()
    private Boolean ongoing;
    @JsonProperty()
    private Integer rating;
}
