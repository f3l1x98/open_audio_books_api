package xyz.f3l1x.open_audio_books_api.app.audio_book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAudioBookRequest {
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
    // TODO strictly speaking there should not be any episodes for this audio book yet
    //@JsonProperty()
    //private List<Long> episodeIds;
    @JsonProperty()
    private List<Long> genreIds;
}
