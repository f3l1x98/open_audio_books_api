package xyz.f3l1x.core.shared;

import java.util.Optional;

public interface IBaseRepository<T extends BaseModel> {
    Optional<T> findById(Long id);
    T save(T model);
}
