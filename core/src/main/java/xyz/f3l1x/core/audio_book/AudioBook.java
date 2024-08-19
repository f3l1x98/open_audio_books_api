package xyz.f3l1x.core.audio_book;

import lombok.*;
import xyz.f3l1x.core.audio_book.exception.AuthorRequiredException;
import xyz.f3l1x.core.audio_book.exception.NarratorRequiredException;
import xyz.f3l1x.core.audio_book.exception.UniqueEpisodeViolationException;
import xyz.f3l1x.core.author.Author;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.shared.BaseModel;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AudioBook extends BaseModel {
    private String title;
    private String summary;
    private Date releaseDate;
    private Boolean ongoing;
    private Integer rating;

    private List<Episode> episodes;
    private Set<Genre> genres;
    private List<Author> authors;
    private List<Narrator> narrators;

    private String directoryName;

    public static AudioBook create(String title, String summary, Date releaseDate, Boolean ongoing, Integer rating, List<Genre> genres, List<Author> authors, List<Narrator> narrators) throws AuthorRequiredException, NarratorRequiredException {
        if (authors.isEmpty()) {
            throw new AuthorRequiredException();
        }
        if (narrators.isEmpty()) {
            throw new NarratorRequiredException();
        }

        return new AudioBook(title, summary, releaseDate, ongoing, rating, new ArrayList<>(), new HashSet<>(genres), authors, narrators);
    }

    public AudioBook(String title, String summary, Date releaseDate, Boolean ongoing, Integer rating, List<Episode> episodes, Set<Genre> genres, List<Author> authors, List<Narrator> narrators) {
        super();
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.ongoing = ongoing;
        this.rating = rating;
        this.episodes = episodes;
        this.genres = genres;
        this.authors = authors;
        this.narrators = narrators;
        this.directoryName = this.getId().toString();
    }

    public void update(String title, String summary, Date releaseDate, Boolean ongoing, Integer rating) {
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.ongoing = ongoing;
        this.rating = rating;
    }

    public Episode addNewEpisode(Integer number, String title, String summary, Date releaseDate, Long duration) throws UniqueEpisodeViolationException {
        boolean numberAlreadyInUse = episodes.stream().anyMatch(episode -> Objects.equals(episode.getNumber(), number));
        boolean titleAlreadyInUse = episodes.stream().anyMatch(episode -> Objects.equals(episode.getTitle(), title));

        if (numberAlreadyInUse || titleAlreadyInUse) {
            throw new UniqueEpisodeViolationException(numberAlreadyInUse, titleAlreadyInUse);
        }

        Episode newEpisode = Episode.create(number, title, summary, releaseDate, duration, this);
        episodes.add(newEpisode);
        return newEpisode;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }
}
