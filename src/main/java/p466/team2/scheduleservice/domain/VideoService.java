package p466.team2.scheduleservice.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Iterable<Video> viewSchedule() {
        Iterable<Video> videos = videoRepository.findAll();
        List<Video> scheduleAsList = new ArrayList<>();
        for (Video video : videos) {
            scheduleAsList.add(video);
        }

        Collections.sort(scheduleAsList);

        return scheduleAsList;
    }

    public Video viewVideoDetails(String title){
        return videoRepository.findByTitle(title).orElseThrow(() -> new VideoNotFoundException(title));
    }

    public Video viewNextVideo() {
        return viewSchedule().iterator().next();
    }

    public Video addVideoToSchedule(Video video) {
        if (videoRepository.existsByTitle(video.title)) {
            throw new VideoAlreadyExistsException(video.getTitle());
        }
        /*
        else if (videoRepository.overlapsByDuration(video)) {
            throw new VideoOverlapsWithAnotherException(video.getTitle());
        }

         */
        return videoRepository.save(video);
    }

    public void removeVideoFromSchedule(Long id) {
        videoRepository.deleteById(id);
    }

    public Video editVideoDetails(String title, Video video) {
        return videoRepository.findByTitle(title).map(existingVideo -> {
            var videoToUpdate = new Video(
                            existingVideo.getTitle(),
                            video.getDate(),
                            video.getStart(),
                            video.getDuration(),
                            video.getLink());
                            videoToUpdate.setId(existingVideo.getId());
                            videoToUpdate.setVersion(existingVideo.getVersion());
                            videoToUpdate.setCreatedDate(existingVideo.getCreatedDate());
                            videoToUpdate.setLastModifiedDate(existingVideo.getLastModifiedDate());
            return videoRepository.save(videoToUpdate);
                }).orElseGet(() -> addVideoToSchedule(video));
    }
}