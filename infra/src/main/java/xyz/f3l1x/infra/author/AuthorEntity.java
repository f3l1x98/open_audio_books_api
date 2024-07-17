package xyz.f3l1x.infra.author;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.f3l1x.infra.audio_book.AudioBookEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Data
@NoArgsConstructor
public class AuthorEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column()
    private String middleName;

    @Column()
    private String lastName;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<AudioBookEntity> audioBooks;
}
