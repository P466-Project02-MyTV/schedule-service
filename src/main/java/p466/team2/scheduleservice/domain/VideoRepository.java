package p466.team2.scheduleservice.domain;

import java.util.Optional;

public interface VideoRepository {
    Iterable<Video> findAll();
    Optional<Video> findByTitle(String title);
    boolean existsByTitle(String title);
    boolean overlapsByDuration(Video video);
    Video save(Video video);
    void deleteByTitle(String title);
}
