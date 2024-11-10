package xyz.f3l1x.open_audio_books_api;

import org.springframework.stereotype.Component;

@Component("dummy")
public class DummyBean {
    public String getText() {
        return "Hello World";
    }
}
