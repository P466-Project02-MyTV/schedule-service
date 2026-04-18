package p466.team2.scheduleservice.domain;

public class VideoAlreadyExistsException extends RuntimeException {
    public VideoAlreadyExistsException(String title) {
        super("A video with Title: " + title + " already exists in the schedule!");
    }
}
