package p466.team2.scheduleservice.domain;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static p466.team2.scheduleservice.domain.Utility.getDurationFromLink;
import static p466.team2.scheduleservice.domain.Utility.getTitleFromLink;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Iterable<Video> viewSchedule() {
        return videoRepository.findAllByOrderByStartDateTimeAsc();
    }

    public Video viewVideoDetails(Long id){
        return videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException(id));
    }

    public Video addVideoToSchedule(Video video) throws IOException {
        // I moved all this logic here from my Video constructor as I'd rather it takes a second to load when adding a
        // video versus the more common action of loading the schedule.
        video.setTitle(getTitleFromLink(video.getLink()));
        video.setDate(video.getStart().substring(0, video.getStart().indexOf("-")));
        String rawDuration = getDurationFromLink(video.getLink());
        String noDecimalDuration = rawDuration.substring(0, rawDuration.indexOf("."));
        long duration = Long.parseLong(noDecimalDuration) + 1; // Rounding up
        video.setDuration(duration + "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy-HH:mm:ss");
        video.setStartDateTime(LocalDateTime.parse(video.getStart(), formatter));
        video.setEndDateTime(video.getStartDateTime().plus(Duration.ofSeconds(duration)));

        boolean overlapping = false;
        for (Video other : videoRepository.findAll()) {
            if (video.getStartDateTime().isBefore(other.getEndDateTime()) && other.getStartDateTime().isBefore(video.getEndDateTime())) {
                overlapping = true;
                break;
            }
        }
        if (overlapping) {
            throw new VideoOverlapsWithAnotherException(video.getTitle());
        }

        return videoRepository.save(video);
    }

    public void removeVideoFromSchedule(Long id) {
        videoRepository.deleteById(id);
    }

    public Video editVideoDetails(Long id, Video video) {
        return videoRepository.findById(id).map(existingVideo -> {
            Video videoToUpdate = null;
            try {
                videoToUpdate = new Video(
                                video.getStart(),
                                video.getLink());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            videoToUpdate.setId(existingVideo.getId());
            videoToUpdate.setVersion(existingVideo.getVersion());
            videoToUpdate.setCreatedDate(existingVideo.getCreatedDate());
            videoToUpdate.setLastModifiedDate(existingVideo.getLastModifiedDate());
            return videoRepository.save(videoToUpdate);
                }).orElseGet(() -> {
            try {
                return addVideoToSchedule(video);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}