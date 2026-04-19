package p466.team2.scheduleservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    @Test
    void whenVideoToCreateAlreadyExistsThenThrows() {
        var title = "Last Video";
        var videoToCreate = new Video(title, "12/20/2010", "12:30", "01:00", "filler");
        when(videoRepository.existsByTitle(title)).thenReturn(true);
        assertThatThrownBy(() -> videoService.addVideoToSchedule(videoToCreate))
                .isInstanceOf(VideoAlreadyExistsException.class)
                .hasMessage("A video with Title: " + title + " already exists in the schedule!");
    }

    /*
    @Test
    void whenVideoToCreateOverlapsWithAnotherThenThrows() {
        var title = "Last Video";
        var videoToCreate = new Video(title, "12/20/2010", "12:30", "01:00", "filler");
        when(videoRepository.overlapsByDuration(videoToCreate)).thenReturn(true);
        assertThatThrownBy(() -> videoService.addVideoToSchedule(videoToCreate))
                .isInstanceOf(VideoOverlapsWithAnotherException.class)
                .hasMessage("A video with Title: " + title + " overlaps with another video in the schedule!");
    }

     */
}
