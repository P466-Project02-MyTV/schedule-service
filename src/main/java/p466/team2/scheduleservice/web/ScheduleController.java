package p466.team2.scheduleservice.web;

import p466.team2.scheduleservice.domain.Video;
import p466.team2.scheduleservice.domain.VideoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final VideoService videoService;

    public ScheduleController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public Iterable<Video> get() {
        return videoService.viewSchedule();
    }

    @GetMapping("{title}")
    public Video getByTitle(@PathVariable String title) {
        return videoService.viewVideoDetails(title);
    }

    @GetMapping("/now")
    public Video getNextVideo() {
        return videoService.viewNextVideo();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Video post(@Valid @RequestBody Video video) {
        return videoService.addVideoToSchedule(video);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        videoService.removeVideoFromSchedule(id);
    }

    @PutMapping("{title}")
    public Video put(@PathVariable String title, @Valid @RequestBody Video video) {
        return videoService.editVideoDetails(title, video);
    }
}