package xyz.f3l1x.core.narrator.query.find_all;

import java.util.Optional;
import java.util.UUID;

public record FindAllNarratorQuery(Optional<UUID> audioBookId) {
}
