package p466.team2.scheduleservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import p466.team2.scheduleservice.domain.Video;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class VideoJsonTests {

    @Autowired
    private JacksonTester<Video> json;

    @Test
    void testSerialize() throws Exception {
        var video = new Video("Last Video", "12/20/2010", "12:30", "01:00", "filler");
        var jsonContent = json.write(video);
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(video.getTitle());
        assertThat(jsonContent).extractingJsonPathStringValue("@.date").isEqualTo(video.getDate());
        assertThat(jsonContent).extractingJsonPathStringValue("@.start").isEqualTo(video.getStart());
        assertThat(jsonContent).extractingJsonPathStringValue("@.duration").isEqualTo(video.getDuration());
        assertThat(jsonContent).extractingJsonPathStringValue("@.link").isEqualTo(video.getLink());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                    "title": "Last Video",
                    "date": "12/20/2010",
                    "start": "12:30",
                    "duration": "01:00",
                    "link": "filler"
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Video("Last Video", "12/20/2010", "12:30", "01:00", "filler"));
    }
}