package xyz.f3l1x.core.narrator.command.delete;

import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.narrator.exception.NarratorNotFoundException;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;

import java.util.Optional;

public class DeleteNarratorCommandHandler implements ICommandHandler<DeleteNarratorCommand, Narrator> {
    private final INarratorRepository repository;

    public DeleteNarratorCommandHandler(INarratorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Narrator handle(DeleteNarratorCommand command) throws NarratorNotFoundException {
        Optional<Narrator> narratorOptional = repository.findById(command.narratorId());
        if (narratorOptional.isEmpty()) {
            throw new NarratorNotFoundException(command.narratorId());
        }
        Narrator narrator = narratorOptional.get();

        repository.delete(narrator);
        return narrator;
    }
}
