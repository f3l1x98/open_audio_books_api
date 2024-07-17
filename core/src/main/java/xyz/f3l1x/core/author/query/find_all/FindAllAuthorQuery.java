package xyz.f3l1x.core.author.query.find_all;

import java.util.Optional;
import java.util.UUID;

public record FindAllAuthorQuery(Optional<UUID> audioBookId) {
}
