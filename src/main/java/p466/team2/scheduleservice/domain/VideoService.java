package p466.team2.scheduleservice.domain;

import org.springframework.stereotype.Service;

import java.io.IOException;

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

    public Video addVideoToSchedule(Video video) {
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
                }).orElseGet(() -> addVideoToSchedule(video));
    }
}