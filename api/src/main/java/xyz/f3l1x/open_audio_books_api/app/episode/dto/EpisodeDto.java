package xyz.f3l1x.open_audio_books_api.app.episode.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.f3l1x.open_audio_books_api.app.audio_book.dto.AudioBookDto;

import java.util.Date;

@Data
@NoArgsConstructor
public class EpisodeDto {
    private Long id;
    private Integer number;
    private String title;
    private String summary;
    private Date releaseDate;

    // TODO ?!?!? private AudioBookDto audioBook;
}
