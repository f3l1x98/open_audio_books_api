package xyz.f3l1x.core.narrator.query.find_all;

import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FindAllNarratorQueryHandler implements IQueryHandler<FindAllNarratorQuery, List<Narrator>> {
    private final INarratorRepository repository;

    public FindAllNarratorQueryHandler(INarratorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Narrator> handle(FindAllNarratorQuery query) {
        Optional<UUID> audioBookId = query.audioBookId();
        List<Narrator> narrators;
        if (audioBookId.isPresent()) {
            narrators = repository.findAllForAudioBook(audioBookId.get());
        } else {
            narrators = repository.findAll();
        }
        return narrators;
    }
}
