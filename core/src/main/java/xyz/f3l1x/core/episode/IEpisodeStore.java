package xyz.f3l1x.core.episode;

import java.io.InputStream;

public interface IEpisodeStore {
    InputStream getContent(Episode episode);
    Episode setContent(Episode episode, InputStream inputStream);
}
