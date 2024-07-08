package xyz.f3l1x.core.shared.cqrs;

public interface ICommandHandler<C, R> {
    R handle(C command) throws Exception;
}
