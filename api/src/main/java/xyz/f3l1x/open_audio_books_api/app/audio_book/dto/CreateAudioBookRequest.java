package xyz.f3l1x.open_audio_books_api.app.audio_book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @JsonProperty()
    private List<UUID> genreIds;
    @JsonProperty()
    @NotEmpty(message = "At least one author required")
    private List<UUID> authorIds;
    @JsonProperty()
    @NotEmpty(message = "At least one narrator required")
    private List<UUID> narratorIds;
}
