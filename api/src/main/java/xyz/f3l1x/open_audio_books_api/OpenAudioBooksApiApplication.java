package xyz.f3l1x.open_audio_books_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "xyz.f3l1x")
public class OpenAudioBooksApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAudioBooksApiApplication.class, args);
    }

}
