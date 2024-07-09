package xyz.f3l1x.infra.audio_book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IJpaAudioBookRepository extends JpaRepository<AudioBookEntity, Long> {
}
