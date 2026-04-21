package p466.team2.scheduleservice.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Need to create another interface and extend it here for overlapping durations.
@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {
    List<Video> findAllByOrderByStartDateTimeAsc();
}
