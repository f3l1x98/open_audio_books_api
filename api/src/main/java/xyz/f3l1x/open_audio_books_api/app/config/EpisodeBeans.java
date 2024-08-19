package xyz.f3l1x.open_audio_books_api.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.f3l1x.core.episode.Episode;
import xyz.f3l1x.core.episode.IEpisodeRepository;
import xyz.f3l1x.core.episode.IEpisodeStore;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommand;
import xyz.f3l1x.core.episode.command.delete.DeleteEpisodeCommandHandler;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommand;
import xyz.f3l1x.core.episode.command.update.UpdateEpisodeCommandHandler;
import xyz.f3l1x.core.episode.command.upload.UploadCommand;
import xyz.f3l1x.core.episode.command.upload.UploadCommandHandler;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQuery;
import xyz.f3l1x.core.episode.query.find_all_for_audio_book.FindAllForAudioBookEpisodeQueryHandler;
import xyz.f3l1x.core.episode.query.get_file.GetFileQuery;
import xyz.f3l1x.core.episode.query.get_file.GetFileQueryHandler;
import xyz.f3l1x.core.episode.query.get_file.GetFileQueryResult;
import xyz.f3l1x.core.shared.cqrs.ICommandHandler;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.List;

@Configuration
public class EpisodeBeans {

    @Bean
    public IQueryHandler<FindAllForAudioBookEpisodeQuery, List<Episode>> findAllForAudioBookEpisodeQueryHandler(IEpisodeRepository episodeRepository) {
        return new FindAllForAudioBookEpisodeQueryHandler(episodeRepository);
    }

    @Bean
    public ICommandHandler<DeleteEpisodeCommand, Episode> deleteEpisodeCommandHandler(IEpisodeRepository episodeRepository) {
        return new DeleteEpisodeCommandHandler(episodeRepository);
    }

    @Bean
    public ICommandHandler<UpdateEpisodeCommand, Episode> updateEpisodeCommandHandler(IEpisodeRepository episodeRepository) {
        return new UpdateEpisodeCommandHandler(episodeRepository);
    }

    @Bean
    public IQueryHandler<GetFileQuery, GetFileQueryResult> getFileQueryHandler(IEpisodeRepository episodeRepository, IEpisodeStore episodeStore) {
        return new GetFileQueryHandler(episodeRepository, episodeStore);
    }

    @Bean
    public ICommandHandler<UploadCommand, Episode> uploadCommandHandler(IEpisodeRepository episodeRepository, IEpisodeStore episodeStore) {
        return new UploadCommandHandler(episodeRepository, episodeStore);
    }
}
