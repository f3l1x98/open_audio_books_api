package xyz.f3l1x.core.episode.query.get_file;

import java.io.InputStream;

public record GetFileQueryResult(InputStream stream, Long contentLength, String mimeType) {
}
