package xyz.f3l1x.core.shared;

public interface IBaseRepository<T extends BaseModel> {
    T save(T model);
}
