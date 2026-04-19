package p466.team2.scheduleservice.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import p466.team2.scheduleservice.domain.Video;
import p466.team2.scheduleservice.domain.VideoRepository;

import java.util.List;

@Component
@Profile("testdata")
public class VideoDataLoader {
    private final VideoRepository videoRepository;

    public VideoDataLoader(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadVideoTestData() {
        // Starting from a fresh database.
        videoRepository.deleteAll();
        var firstVideo = new Video("First Video", "12/20/2000", "12:30", "01:00", "filler");
        var middleVideo = new Video("Middle Video", "12/20/2005", "12:30", "01:00", "filler");
        var lastVideo = new Video("Last Video", "12/20/2010", "12:30", "01:00", "filler");
        videoRepository.saveAll(List.of(middleVideo, lastVideo, firstVideo));
    }
}
