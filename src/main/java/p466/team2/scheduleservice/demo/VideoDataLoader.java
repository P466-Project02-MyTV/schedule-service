package p466.team2.scheduleservice.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import p466.team2.scheduleservice.domain.Video;
import p466.team2.scheduleservice.domain.VideoRepository;

import java.io.IOException;
import java.util.List;

@Component
@Profile("testdata")
public class VideoDataLoader {
    private final VideoRepository videoRepository;

    public VideoDataLoader(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadVideoTestData() throws IOException {
        // Starting from a fresh database.
        videoRepository.deleteAll();
        var firstVideo = new Video("12/20/2000-12:30:00", "https://youtu.be/QP4ExX773B4?si=iWQqN4zGZUDty6se");
        var middleVideo = new Video("12/20/2005-15:30:00", "https://youtu.be/cDfPt0bX90E?si=Ji_gWR6JP6HApthe");
        var lastVideo = new Video("12/20/2010-18:30:00", "https://youtu.be/ob2Wxc4pgyE?si=b06TqEL_eI8BSWgK");
        videoRepository.saveAll(List.of(middleVideo, lastVideo, firstVideo));
    }
}
