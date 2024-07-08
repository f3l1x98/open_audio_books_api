package xyz.f3l1x.infra.audio_book;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;

import java.util.List;
import java.util.Optional;

public class JpaAudioBookRepository implements IAudioBookRepository {

    // TODO unsure how mapper handles list of nested objects: https://www.baeldung.com/java-modelmapper-lists
    private final ModelMapper mapper;
    private final IJpaAudioBookRepository repository;

    public JpaAudioBookRepository(ModelMapper mapper, IJpaAudioBookRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<AudioBook> findAll() {
        return repository.findAll()
                .stream().map(audioBookEntity -> mapper.map(audioBookEntity, AudioBook.class))
                .toList();
    }

    @Override
    public Optional<AudioBook> findById(Long id) {
        Optional<AudioBookEntity> entity = repository.findById(id);
        return mapper.map(entity, new TypeToken<Optional<AudioBook>>() {}.getType());
    }

    @Override
    public AudioBook save(AudioBook audioBook) {
        AudioBookEntity saved = repository.save(mapper.map(audioBook, AudioBookEntity.class));
        return mapper.map(saved, AudioBook.class);
    }
}
