package xyz.f3l1x.open_audio_books_api.audio_book.list;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omnifaces.util.Messages;
import org.springframework.stereotype.Component;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.query.find_all.FindAllAudioBookQuery;
import xyz.f3l1x.core.shared.cqrs.IQueryHandler;

import java.util.ArrayList;
import java.util.List;

@Component("audioBookListModel")
@ViewScoped
public class AudioBookListModel {
    @Getter
    private List<AudioBookDto> audioBooks = new ArrayList<>();

    private final ModelMapper mapper;
    private final IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler;

    public AudioBookListModel(
            ModelMapper mapper,
            IQueryHandler<FindAllAudioBookQuery, List<AudioBook>> findAllAudioBookQueryHandler
    ) {
        this.mapper = mapper;
        this.findAllAudioBookQueryHandler = findAllAudioBookQueryHandler;
    }

    @PostConstruct
    private void init() {
        try {
            final FindAllAudioBookQuery query = new FindAllAudioBookQuery(null, null);
            final List<AudioBook> result = findAllAudioBookQueryHandler.handle(query);
            this.audioBooks = this.mapper.map(result, new TypeToken<List<AudioBookDto>>() {}.getType());
        } catch (Exception e) {
            Messages.create("Failed to load data").error().add();
        }
    }
}
