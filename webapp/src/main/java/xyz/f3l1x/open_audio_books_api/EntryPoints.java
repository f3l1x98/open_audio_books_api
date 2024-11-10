package xyz.f3l1x.open_audio_books_api;

import lombok.Getter;

@Getter
public enum EntryPoints {
    HOME("/home"),
    AUDIOBOOK_LIST("/audioBook/list"),
    AUDIOBOOK_DETAIL("/audioBook/detail");

    private final String url;

    EntryPoints(String url) {
        this.url = url;
    }
}
