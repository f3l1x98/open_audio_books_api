package xyz.f3l1x.infra.genre;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.f3l1x.infra.audio_book.AudioBookEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
public class GenreEntity {
    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<AudioBookEntity> audioBooks;
}
