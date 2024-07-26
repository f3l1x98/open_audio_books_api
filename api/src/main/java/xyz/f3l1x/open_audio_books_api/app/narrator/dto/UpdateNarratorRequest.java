package xyz.f3l1x.open_audio_books_api.app.narrator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNarratorRequest {
    @JsonProperty()
    private String firstName;
    @JsonProperty()
    private String middleName;
    @JsonProperty()
    private String lastName;
}
