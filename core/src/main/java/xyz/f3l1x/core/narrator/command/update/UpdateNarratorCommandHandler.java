package xyz.f3l1x.core.narrator.command.update;

import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.narrator.exception.NarratorNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class UpdateNarratorCommandHandler implements ICommandHandler<UpdateNarratorCommand, Narrator> {
    private final INarratorRepository repository;

    public UpdateNarratorCommandHandler(INarratorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Narrator handle(UpdateNarratorCommand command) throws NarratorNotFoundException {
        Optional<Narrator> current = repository.findById(command.id());
        if (current.isEmpty()) {
            throw new NarratorNotFoundException(command.id());
        }
        Narrator narrator = current.get();
        narrator.update(command.firstName(), command.middleName(), command.lastName());

        return repository.save(narrator);
    }
}
