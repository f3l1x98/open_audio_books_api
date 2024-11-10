package xyz.f3l1x.open_audio_books_api.config.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.f3l1x.core.narrator.INarratorRepository;
import xyz.f3l1x.core.narrator.Narrator;
import xyz.f3l1x.core.narrator.command.create.CreateNarratorCommand;
import xyz.f3l1x.core.narrator.command.create.CreateNarratorCommandHandler;
import xyz.f3l1x.core.narrator.command.delete.DeleteNarratorCommand;
import xyz.f3l1x.core.narrator.command.delete.DeleteNarratorCommandHandler;
import xyz.f3l1x.core.narrator.command.update.UpdateNarratorCommand;
import xyz.f3l1x.core.narrator.command.update.UpdateNarratorCommandHandler;
import xyz.f3l1x.core.narrator.query.find_all.FindAllNarratorQuery;
import xyz.f3l1x.core.narrator.query.find_all.FindAllNarratorQueryHandler;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;

@Configuration
public class NarratorBeans {

    @Bean
    public IQueryHandler<FindAllNarratorQuery, List<Narrator>> findAllNarratorQueryHandler(INarratorRepository narratorRepository) {
        return new FindAllNarratorQueryHandler(narratorRepository);
    }

    @Bean
    public ICommandHandler<CreateNarratorCommand, Narrator> createNarratorCommandHandler(INarratorRepository narratorRepository) {
        return new CreateNarratorCommandHandler(narratorRepository);
    }

    @Bean
    public ICommandHandler<UpdateNarratorCommand, Narrator> updateNarratorCommandHandler(INarratorRepository narratorRepository) {
        return new UpdateNarratorCommandHandler(narratorRepository);
    }

    @Bean
    public ICommandHandler<DeleteNarratorCommand, Narrator> deleteNarratorCommandHandler(INarratorRepository narratorRepository) {
        return new DeleteNarratorCommandHandler(narratorRepository);
    }
}
