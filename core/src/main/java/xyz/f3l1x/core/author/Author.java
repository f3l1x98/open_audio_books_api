package xyz.f3l1x.core.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.shared.BaseModel;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseModel {
    private String firstName;
    private String middleName;
    private String lastName;

    private List<AudioBook> audioBooks;

    public static Author create(String firstName, String middleName, String lastName) {
        return new Author(firstName, middleName, lastName, new ArrayList<>());
    }

    public void update(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
}
