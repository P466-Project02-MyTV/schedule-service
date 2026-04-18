package p466.team2.scheduleservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import p466.team2.scheduleservice.domain.Video;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScheduleServiceApplicationTests {

    /*
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetRequestThenNextVideoReturned() {
        var title = "Last Video";
        var videoToCreate = new Video(title, "12/20/2010", "12:30", "01:00", "filler");
        Video expectedVideo = webTestClient
                .post()
                .uri("/schedule")
                .bodyValue(videoToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Video.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/schedule/now")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Video.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.getTitle()).isEqualTo(expectedVideo.getTitle());
                });
    }

     */

}
