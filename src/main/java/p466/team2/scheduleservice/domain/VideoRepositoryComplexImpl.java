package p466.team2.scheduleservice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoRepositoryComplexImpl implements VideoRepositoryComplex {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean checkIfVideosOverlap(Video video) {
        String sql = "SELECT title, date, start, duration, link FROM video";
        List<Video> schedule = jdbcTemplate.query(sql, (rs, rowNum) -> new Video(
                rs.getString("title"),
                rs.getString("date"),
                rs.getString("start"),
                rs.getString("duration"),
                rs.getString("link")));

        for (Video other : schedule) {
            if (video.getStartDateTime().isBefore(other.getEndDateTime()) && other.getStartDateTime().isBefore(video.getEndDateTime())) {
                return true;
            }
        }

        return false;
    }
}
