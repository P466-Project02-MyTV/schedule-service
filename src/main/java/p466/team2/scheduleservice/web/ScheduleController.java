package p466.team2.scheduleservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import p466.team2.scheduleservice.domain.Video;
import p466.team2.scheduleservice.domain.VideoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final VideoService videoService;

    public ScheduleController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public String get(Model model) {
        Iterable<Video> schedule = videoService.viewSchedule();
        model.addAttribute("schedule", schedule);
        return "schedule";
        //return videoService.viewSchedule();
    }

    @GetMapping("{id}")
    public Video getById(@PathVariable Long id) {
        return videoService.viewVideoDetails(id);
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

    @PutMapping("{id}")
    public Video put(@PathVariable Long id, @Valid @RequestBody Video video) {
        return videoService.editVideoDetails(id, video);
    }
}