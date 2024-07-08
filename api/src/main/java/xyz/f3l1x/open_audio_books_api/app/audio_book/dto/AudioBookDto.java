package xyz.f3l1x.open_audio_books_api.app.audio_book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.f3l1x.open_audio_books_api.app.episode.dto.EpisodeDto;
import xyz.f3l1x.open_audio_books_api.app.genre.dto.GenreDto;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AudioBookDto {
    private Long id;
    private String title;
    private String summary;
    private Date releaseDate;
    private Boolean ongoing;
    private Integer rating;

    private List<EpisodeDto> episodes;
    private List<GenreDto> genres;
}
