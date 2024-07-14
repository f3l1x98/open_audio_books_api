package xyz.f3l1x.infra.episode;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import xyz.f3l1x.infra.audio_book.AudioBookEntity;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "episode")
@Data
@NoArgsConstructor
public class EpisodeEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String title;

    @Column()
    private String summary;

    @Column(nullable = false)
    private Timestamp releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audio_book_id", nullable = false)
    private AudioBookEntity audioBook;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updatedDate;
}
