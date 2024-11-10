package xyz.f3l1x.infra.episode;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;
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

    @Column(nullable = false)
    private Long duration;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updatedDate;

    // TODO UNSURE WHAT ContentId IS USED FOR (setContent does not set a contentId)
    //  - However according to docs it is updated on setContent
    //  - Normally it is used to get file location, HOWEVER in this case I have a custom Converter that changes this behaviour (needed for subdir by audioBook)
    @ContentId
    @Transient
    //@Column(nullable = false)
    private UUID contentId;

    @ContentLength
    @Column(nullable = false)
    private Long contentLength = 0L;

    @MimeType
    @Column(nullable = false)
    private String contentMimeType = "audio/mpeg";

    @Column(nullable = false)
    private String fileName;
}
