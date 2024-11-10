package xyz.f3l1x.open_audio_books_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class OpenAudioBooksJSFApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenAudioBooksJSFApplication.class, args);
    }
}
