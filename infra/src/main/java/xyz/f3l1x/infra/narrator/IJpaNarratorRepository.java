package xyz.f3l1x.infra.narrator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IJpaNarratorRepository extends JpaRepository<NarratorEntity, UUID> {
    List<NarratorEntity> findAllByIdIn(List<UUID> ids);
    List<NarratorEntity> findAllByAudioBooks_Id(UUID audioBooksId);
}
