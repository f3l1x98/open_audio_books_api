package xyz.f3l1x.core.author.command.update;

import java.util.UUID;

public record UpdateAuthorCommand(UUID id, String firstName, String middleName, String lastName) {
}
