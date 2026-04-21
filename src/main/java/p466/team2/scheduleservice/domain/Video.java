package p466.team2.scheduleservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Video implements Comparable<Video> {

    @Id
    Long id;

    @Version
    int version;

    @NotBlank(message = "The video's Title must be defined.")
    String title;

    @NotBlank(message = "The video's Date must be defined.")
    @Pattern(
            regexp = "^(0[1-9]|1[0-2])([/-])(0[1-9]|[12][0-9]|3[01])\\2(19|20)\\d{2}$",
            message = "The video's Date format must be correct. (mm/dd/yyyy)"
    )
    String date;

    @NotBlank(message = "The video's Start time must be defined.")
    @Pattern(
            regexp = "^([0-9]{1,2}:)?[0-5][0-9]:[0-5][0-9]$",
            message = "The video's Start time format must be correct. (hh:mm)"
    )
    String start;

    @NotBlank(message = "The video's Duration must be defined.")
    @Pattern(
            regexp = "^P(?!$)(\\d+Y)?(\\d+M)?(\\d+W)?(\\d+D)?(T(?=\\d)(\\d+H)?(\\d+M)?(\\d+S)?)?$",
            message = "The video's Duration format must be correct. (hh:mm:ss)"
    )
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
        String title = getTitleFromLink(link);
        String date = start.substring(0, start.indexOf("-"));
        String rawDuration = getDurationFromLink(link);
        String noDecimalDuration = rawDuration.substring(0, rawDuration.indexOf("."));
        long duration = Long.parseLong(noDecimalDuration) + 1; // Rounding up
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy-HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = startDateTime.plus(Duration.ofSeconds(duration));

        this.id = null;
        this.version = 0;
        this.title = title;
        this.date = date;
        this.start = start;
        this.duration = Long.toString(duration);
        this.link = link;
        this.createdDate = null;
        this.lastModifiedDate = null;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public int compareTo(@NonNull Video other) {
        if (this.getStartDateTime().isBefore(other.getStartDateTime())) {
            return -1;
        } else if (other.getStartDateTime().isBefore(this.getStartDateTime())) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getDurationFromLink(String videoUrl) throws IOException {
        String streamUrl = executeCommand("yt-dlp", "-g", "-f", "best", videoUrl);

        return executeCommand("ffprobe", "-v", "error",
                "-show_entries", "format=duration",
                "-of", "default=noprint_wrappers=1:nokey=1",
                streamUrl);
    }

    public String getTitleFromLink(String videoUrl) throws IOException {
        return executeCommand("yt-dlp", "--get-title", videoUrl);
    }

    public String executeCommand(String... command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.readLine(); // Returns first line of output
        }
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
