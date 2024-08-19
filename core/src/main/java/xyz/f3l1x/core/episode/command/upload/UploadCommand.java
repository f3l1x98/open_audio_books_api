package xyz.f3l1x.core.episode.command.upload;

import java.io.InputStream;
import java.util.UUID;

public record UploadCommand(UUID episodeId, InputStream input, String mimeType) {
}
