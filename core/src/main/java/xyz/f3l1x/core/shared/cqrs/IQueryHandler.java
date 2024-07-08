package xyz.f3l1x.core.shared.cqrs;

public interface IQueryHandler<Q, R> {
    R handle(Q query);
}
