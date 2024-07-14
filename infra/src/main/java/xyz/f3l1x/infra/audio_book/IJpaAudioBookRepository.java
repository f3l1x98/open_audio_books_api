package xyz.f3l1x.infra.audio_book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IJpaAudioBookRepository extends JpaRepository<AudioBookEntity, UUID> {
}
