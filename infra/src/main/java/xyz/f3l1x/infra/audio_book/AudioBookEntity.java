package xyz.f3l1x.infra.audio_book;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.genre.Genre;
import xyz.f3l1x.infra.episode.EpisodeEntity;
import xyz.f3l1x.infra.genre.GenreEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "audio_book")
@Data
@NoArgsConstructor
public class AudioBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column()
    private String summary;

    @Column(nullable = false)
    private Date releaseDate;

    @Column(columnDefinition = "BOOLEAN NOT NULL default true")
    private Boolean ongoing;

    @Column(columnDefinition = "INTEGER NOT NULL default 0")
    private Integer rating;

    @OneToMany(mappedBy = "audioBook",fetch = FetchType.LAZY)
    private List<EpisodeEntity> episodes;

    @ManyToMany()
    @JoinTable(
            name = "rt_audio_book_genre",
            joinColumns = @JoinColumn(name = "audio_book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<GenreEntity> genres;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updatedDate;
}
