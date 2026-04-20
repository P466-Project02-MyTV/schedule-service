package p466.team2.scheduleservice.domain;

import org.springframework.stereotype.Service;

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
        if (videoRepository.checkIfVideosOverlap(video)) {
            throw new VideoOverlapsWithAnotherException(video.getTitle());
        }
        return videoRepository.save(video);
    }

    public void removeVideoFromSchedule(Long id) {
        videoRepository.deleteById(id);
    }

    public Video editVideoDetails(Long id, Video video) {
        return videoRepository.findById(id).map(existingVideo -> {
            var videoToUpdate = new Video(
                            video.getTitle(),
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