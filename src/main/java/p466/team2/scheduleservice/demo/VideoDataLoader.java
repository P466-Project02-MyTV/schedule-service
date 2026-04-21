package p466.team2.scheduleservice.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import p466.team2.scheduleservice.domain.Video;
import p466.team2.scheduleservice.domain.VideoRepository;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static p466.team2.scheduleservice.domain.Utility.getDurationFromLink;
import static p466.team2.scheduleservice.domain.Utility.getTitleFromLink;

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
        firstVideo.setTitle(getTitleFromLink(firstVideo.getLink()));
        firstVideo.setDate(firstVideo.getStart().substring(0, firstVideo.getStart().indexOf("-")));
        String rawDuration = getDurationFromLink(firstVideo.getLink());
        String noDecimalDuration = rawDuration.substring(0, rawDuration.indexOf("."));
        long duration = Long.parseLong(noDecimalDuration) + 1; // Rounding up
        firstVideo.setDuration(duration + "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy-HH:mm:ss");
        firstVideo.setStartDateTime(LocalDateTime.parse(firstVideo.getStart(), formatter));
        firstVideo.setEndDateTime(firstVideo.getStartDateTime().plus(Duration.ofSeconds(duration)));

        var middleVideo = new Video("12/20/2005-15:30:00", "https://youtu.be/cDfPt0bX90E?si=Ji_gWR6JP6HApthe");
        middleVideo.setTitle(getTitleFromLink(middleVideo.getLink()));
        middleVideo.setDate(middleVideo.getStart().substring(0, middleVideo.getStart().indexOf("-")));
        rawDuration = getDurationFromLink(middleVideo.getLink());
        noDecimalDuration = rawDuration.substring(0, rawDuration.indexOf("."));
        duration = Long.parseLong(noDecimalDuration) + 1; // Rounding up
        middleVideo.setDuration(duration + "");
        middleVideo.setStartDateTime(LocalDateTime.parse(middleVideo.getStart(), formatter));
        middleVideo.setEndDateTime(middleVideo.getStartDateTime().plus(Duration.ofSeconds(duration)));

        var lastVideo = new Video("12/20/2010-18:30:00", "https://youtu.be/ob2Wxc4pgyE?si=b06TqEL_eI8BSWgK");
        lastVideo.setTitle(getTitleFromLink(lastVideo.getLink()));
        lastVideo.setDate(lastVideo.getStart().substring(0, lastVideo.getStart().indexOf("-")));
        rawDuration = getDurationFromLink(lastVideo.getLink());
        noDecimalDuration = rawDuration.substring(0, rawDuration.indexOf("."));
        duration = Long.parseLong(noDecimalDuration) + 1; // Rounding up
        lastVideo.setDuration(duration + "");
        lastVideo.setStartDateTime(LocalDateTime.parse(lastVideo.getStart(), formatter));
        lastVideo.setEndDateTime(lastVideo.getStartDateTime().plus(Duration.ofSeconds(duration)));

        videoRepository.saveAll(List.of(firstVideo, middleVideo, lastVideo));
    }
}
