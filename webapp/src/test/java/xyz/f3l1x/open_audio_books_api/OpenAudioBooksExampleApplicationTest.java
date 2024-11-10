package xyz.f3l1x.open_audio_books_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = OpenAudioBooksJSFApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class OpenAudioBooksExampleApplicationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHelloFromSpring() {
        ResponseEntity<String> entity = restTemplate.getForEntity("/index.xhtml", String.class);

        checkResponse(entity);
    }

    @Test
    public void testWelcomePageRedirect() {
        ResponseEntity<String> entity = restTemplate.getForEntity("/", String.class);

        checkResponse(entity);
    }

    private static void checkResponse(ResponseEntity<String> entity) {
        assertThat(entity.getStatusCode().is2xxSuccessful()).isTrue();

        assertThat(entity.getBody()).contains("Hello from Spring:");
    }
}