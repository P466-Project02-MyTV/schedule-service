package p466.team2.scheduleservice.domain;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(String title) {
        super("A video with Title: " + title + " does not exist in the database!");
    }
}
