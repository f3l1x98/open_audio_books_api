package xyz.f3l1x.infra.narrator;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.f3l1x.infra.audio_book.AudioBookEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "narrator")
@Data
@NoArgsConstructor
public class NarratorEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column()
    private String middleName;

    @Column()
    private String lastName;

    @ManyToMany(mappedBy = "narrators", fetch = FetchType.LAZY)
    private List<AudioBookEntity> audioBooks;
}
