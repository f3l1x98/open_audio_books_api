package xyz.f3l1x.open_audio_books_api.app.audio_book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AudioBookDto {
    private UUID id;
    private String title;
    private String summary;
    private Date releaseDate;
    private Boolean ongoing;
    private Integer rating;

    private List<UUID> episodeIds;
    private List<UUID> genreIds;
    private List<UUID> authorIds;
    private List<UUID> narratorIds;
}
