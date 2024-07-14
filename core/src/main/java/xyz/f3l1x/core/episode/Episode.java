package xyz.f3l1x.core.episode;

import lombok.*;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.shared.BaseModel;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Episode extends BaseModel {
    private Integer number;
    private String title;
    private String summary;
    private Date releaseDate;

    private AudioBook audioBook;

    public static Episode create(Integer number, String title, String summary, Date releaseDate, AudioBook audioBook) {
        return new Episode(number, title, summary, releaseDate, audioBook);
    }

    public void update(Integer number, String title, String summary, Date releaseDate) {
        this.number = number;
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
    }
}
