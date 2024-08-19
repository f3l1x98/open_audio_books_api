package xyz.f3l1x.core.episode;

import lombok.*;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.shared.BaseModel;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Episode extends BaseModel {
    private Integer number;
    private String title;
    private String summary;
    private Date releaseDate;
    /** Duration of episode in seconds */
    private Long duration;

    private AudioBook audioBook;

    private String fileName;

    private Long contentLength;
    private String mimeType;

    public static Episode create(Integer number, String title, String summary, Date releaseDate, Long duration, AudioBook audioBook) throws IllegalArgumentException {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }

        return new Episode(number, title, summary, releaseDate, duration, audioBook);
    }

    public Episode(Integer number, String title, String summary, Date releaseDate, Long duration, AudioBook audioBook) {
        this.number = number;
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.audioBook = audioBook;
        this.fileName = "Episode " + number + ".mp3"; // TODO always mp3?!?!?
    }

    public void update(Integer number, String title, String summary, Date releaseDate) {
        this.number = number;
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
    }
}
