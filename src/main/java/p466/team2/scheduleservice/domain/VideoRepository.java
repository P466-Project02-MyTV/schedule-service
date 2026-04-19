package p466.team2.scheduleservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// Need to create another interface and extend it here for overlapping durations.
public interface VideoRepository extends CrudRepository<Video, Long> {
    Optional<Video> findByTitle(String title);
    boolean existsByTitle(String title);


    @Override
    void deleteById(Long id);
}
