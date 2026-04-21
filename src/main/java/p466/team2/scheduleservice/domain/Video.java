package p466.team2.scheduleservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

public class Video {

    @Id
    Long id;

    @Version
    int version;

    String title;

    String date;

    @NotBlank(message = "The video's Start time must be defined.")
    @Pattern(
            regexp = "^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}:\\d{2}$",
            message = "The video's Start time format must be correct. (MM:dd:yyyy-HH:mm:ss)"
    )
    String start;

    String duration;

    @NotBlank(message = "The video's Link must be defined.")
    String link;

    @CreatedDate
    Instant createdDate;

    @LastModifiedDate
    Instant lastModifiedDate;

    LocalDateTime startDateTime;

    LocalDateTime endDateTime;

    public Video(String start, String link) throws IOException {
        this.id = null;
        this.version = 0;
        this.title = null;
        this.date = null;
        this.start = start;
        this.duration = null;
        this.link = link;
        this.createdDate = null;
        this.lastModifiedDate = null;
        this.startDateTime = null;
        this.endDateTime = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
