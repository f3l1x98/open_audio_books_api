package xyz.f3l1x.core.genre.query.find_all;

import java.util.Optional;
import java.util.UUID;

public record FindAllGenreQuery(Optional<UUID> forAudioBook) {
}
