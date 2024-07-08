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

    // TODO enforce no other episodes with same number/title per audioBook
    //  -> unable to use .NET solution with static create() due to required NoArgs constructor for mapper
    //  -> Configure mapper to use either static factory method or lombok builder: https://github.com/modelmapper/modelmapper/issues/265

    public static Episode create(Integer number, String title, String summary, Date releaseDate, AudioBook audioBook) {
        System.out.println("Test Episode factory");
        return new Episode(number, title, summary, releaseDate, audioBook);
    }
}
