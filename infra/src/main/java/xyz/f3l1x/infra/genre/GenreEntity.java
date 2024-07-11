package xyz.f3l1x.infra.genre;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.f3l1x.infra.audio_book.AudioBookEntity;

import java.util.List;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<AudioBookEntity> audioBooks;
}
