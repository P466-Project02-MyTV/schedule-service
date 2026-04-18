package p466.team2.scheduleservice.domain;

public class VideoOverlapsWithAnotherException extends RuntimeException {
    public VideoOverlapsWithAnotherException(String title) {
        super("A video with Title: " + title + " overlaps with another video in the schedule!");
    }
}
