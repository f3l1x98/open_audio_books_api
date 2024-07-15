package xyz.f3l1x.infra.audio_book;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import xyz.f3l1x.core.audio_book.AudioBook;
import xyz.f3l1x.core.audio_book.IAudioBookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JpaAudioBookRepository implements IAudioBookRepository {
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
    public void delete(AudioBook audioBook) {
        repository.delete(mapper.map(audioBook, AudioBookEntity.class));
    }

    @Override
    public Optional<AudioBook> findById(UUID id) {
        Optional<AudioBookEntity> entity = repository.findById(id);
        return mapper.map(entity, new TypeToken<Optional<AudioBook>>() {}.getType());
    }

    @Override
    public AudioBook save(AudioBook audioBook) {
        AudioBookEntity saved = repository.save(mapper.map(audioBook, AudioBookEntity.class));
        return mapper.map(saved, AudioBook.class);
    }
}
