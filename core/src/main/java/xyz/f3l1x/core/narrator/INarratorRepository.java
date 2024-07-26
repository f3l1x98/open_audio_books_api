package xyz.f3l1x.core.narrator;

import xyz.f3l1x.core.shared.IBaseRepository;

import java.util.List;
import java.util.UUID;

public interface INarratorRepository extends IBaseRepository<Narrator> {
    List<Narrator> findAll();
    List<Narrator> findAllByIdIn(List<UUID> ids);
    List<Narrator> findAllForAudioBook(UUID audioBookId);
}
