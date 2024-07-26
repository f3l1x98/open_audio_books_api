package xyz.f3l1x.core.narrator.command.update;

import java.util.UUID;

public record UpdateNarratorCommand(UUID id, String firstName, String middleName, String lastName) {
}
