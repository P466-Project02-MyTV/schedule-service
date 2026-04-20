package p466.team2.scheduleservice.domain;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(Long id) {
        super("A video with Title: " + id + " does not exist in the database!");
    }
}
