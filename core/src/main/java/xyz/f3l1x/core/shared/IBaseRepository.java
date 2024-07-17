package xyz.f3l1x.core.shared;

import java.util.Optional;
import java.util.UUID;

public interface IBaseRepository<T extends BaseModel> {
    Optional<T> findById(UUID id);
    T save(T model);
    void delete(T model);
}
