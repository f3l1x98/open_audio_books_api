package xyz.f3l1x.core.narrator.command.create;

import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

public class CreateNarratorCommandHandler implements ICommandHandler<CreateNarratorCommand, Narrator> {
    private final INarratorRepository repository;

    public CreateNarratorCommandHandler(INarratorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Narrator handle(CreateNarratorCommand command) throws Exception {
        Narrator narrator = Narrator.create(command.firstName(), command.middleName(), command.lastName());
        return repository.save(narrator);
    }
}
