package xyz.f3l1x.core.audio_book;

import lombok.*;
import xyz.f3l1x.core.audio_book.exception.UniqueEpisodeViolationException;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.shared.BaseModel;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioBook extends BaseModel {
    private String title;
    private String summary;
    private Date releaseDate;
    private Boolean ongoing;
    private Integer rating;

    private List<Episode> episodes;
    private Set<Genre> genres;

    public static AudioBook create(String title, String summary, Date releaseDate, Boolean ongoing, Integer rating, List<Genre> genres) {
        return new AudioBook(title, summary, releaseDate, ongoing, rating, new ArrayList<>(), new HashSet<>(genres));
    }

    public void addNewEpisode(Integer number, String title, String summary, Date releaseDate) throws UniqueEpisodeViolationException {
        boolean numberAlreadyInUse = episodes.stream().anyMatch(episode -> Objects.equals(episode.getNumber(), number));
        boolean titleAlreadyInUse = episodes.stream().anyMatch(episode -> Objects.equals(episode.getTitle(), title));

        if (numberAlreadyInUse || titleAlreadyInUse) {
            throw new UniqueEpisodeViolationException(numberAlreadyInUse, titleAlreadyInUse);
        }

        Episode newEpisode = Episode.create(number, title, summary, releaseDate, this);
        this.episodes.add(newEpisode);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }
}
