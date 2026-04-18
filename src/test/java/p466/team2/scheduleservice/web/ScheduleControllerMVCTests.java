package p466.team2.scheduleservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import p466.team2.scheduleservice.domain.Video;
import p466.team2.scheduleservice.domain.VideoAlreadyExistsException;
import p466.team2.scheduleservice.domain.VideoService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerMVCTests {

    /*
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VideoService videoService;

    @Test
    void whenGetScheduleNotExistingThenShouldReturn422() throws Exception {
        String title = "Last Video", date = "12/20/2010", start = "12:30", duration = "01:00", link = "filler";
        Video videoToCreate = new Video(title, date, start, duration, link);;
        given(videoService.addVideoToSchedule(videoToCreate)).willThrow(VideoAlreadyExistsException.class);

        // Should perform a call to add a video here, but I'm not familiar with parameter syntax in urls.
        mockMvc.perform(post("/schedule/")).andExpect(status().isUnprocessableContent());
    }

     */
}
