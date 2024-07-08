package xyz.f3l1x.infra.audio_book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaAudioBookRepository extends JpaRepository<AudioBookEntity, Long> {
}
