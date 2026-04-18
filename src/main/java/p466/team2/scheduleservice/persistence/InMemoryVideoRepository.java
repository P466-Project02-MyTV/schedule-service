package p466.team2.scheduleservice.persistence;

import p466.team2.scheduleservice.domain.Video;
import p466.team2.scheduleservice.domain.VideoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryVideoRepository implements VideoRepository {
    private static final Map<String, Video> schedule = new ConcurrentHashMap<>();

    /**
     * I chose to move the collection from a map to a list and order the video by date and start time.
     * This was to make getting the next, earliest video easy.
     *
     * @return List of videos ordered by date and start time.
     */
    @Override
    public Iterable<Video> findAll() {
        return schedule.values();
    }

    /**
     * Searches the repository for a Video with a matching title to the one given.
     *
     * @param title - The String representing the title of the video.
     * @return If a Video with a matching title is found in the repository, returns an Optional containing said Video.
     *         Otherwise, returns an empty Optional.
     */
    @Override
    public Optional<Video> findByTitle(String title) {
        return existsByTitle(title) ? Optional.of(schedule.get(title)) : Optional.empty();
    }

    /**
     * Helper for findByTitle().
     *
     * @param title - The String representing the title of the video.
     * @return True if there exists a Video in the repository with a matching title. Otherwise, false.
     */
    @Override
    public boolean existsByTitle(String title) {
        return schedule.get(title) != null;
    }

    /**
     * Checks through all the Videos from the repository to see if the given Video's runtime overlaps with another
     * Video's runtime.
     *
     * @param video - A given Video that is potentially going ot be added to the repository.
     * @return True if the given Video overlaps with another Video in the repository. Otherwise, false.
     */
    @Override
    public boolean overlapsByDuration(Video video) {
        for (Video value : schedule.values()) {
            if (video.overlappingTime(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is only invoked if the Video doesn't share the same title as another Video from the repository nor
     * does its runtime overlap with another Video's runtime.
     *
     * @param video - The Video to be saved to the repository.
     * @return The Video being saved to the repository.
     */
    @Override
    public Video save(Video video) {
        schedule.put(video.getTitle(), video);
        return video;
    }

    /**
     * Deletes a Video from the repository that has the matching title that is given.
     * Method call would look like DELETE :9001/schedule/"Video Title"
     *
     * @param title - String representing the potential title of a Video in the repository.
     */
    @Override
    public void deleteByTitle(String title) {
        schedule.remove(title);
    }
}
