package xyz.f3l1x.infra.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IJpaAuthorRepository extends JpaRepository<AuthorEntity, UUID> {
    List<AuthorEntity> findAllByIdIn(List<UUID> ids);
    List<AuthorEntity> findAllByAudioBooks_Id(UUID audioBooksId);
}
