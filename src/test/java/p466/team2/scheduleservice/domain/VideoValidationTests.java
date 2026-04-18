package p466.team2.scheduleservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class VideoValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var video = new Video("Last Video", "12/20/2010", "12:30", "01:00", "filler");
        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenDateNotDefinedThenValidationFails() {
        var video = new Video("Last Video", "", "12:30", "01:00", "filler");
        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertThat(violations).hasSize(2);
        List<String> constraintViolationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages).contains("The video's Date must be defined.").contains("The video's Date format must be correct. (mm/dd/yyyy)");
    }

    @Test
    void whenDateDefinedButIncorrectThenValidationFails() {
        var video = new Video("Last Video", "12/33/2010", "12:30", "01:00", "filler");
        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertThat(violations).hasSize(1);
        List<String> constraintViolationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages).contains("The video's Date format must be correct. (mm/dd/yyyy)");
    }

    @Test
    void whenStartNotDefinedThenValidationFails() {
        var video = new Video("Last Video", "12/20/2010", "", "01:00", "filler");
        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertThat(violations).hasSize(2);
        List<String> constraintViolationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages).contains("The video's Start time must be defined.").contains("The video's Start time format must be correct. (hh:mm)");
    }

    @Test
    void whenStartDefinedButIncorrectThenValidationFails() {
        var video = new Video("Last Video", "12/20/2010", "12:70", "01:00", "filler");
        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertThat(violations).hasSize(1);
        List<String> constraintViolationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages).contains("The video's Start time format must be correct. (hh:mm)");
    }

    @Test
    void whenDurationNotDefinedThenValidationFails() {
        var video = new Video("Last Video", "12/20/2010", "12:30", "", "filler");
        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertThat(violations).hasSize(2);
        List<String> constraintViolationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages).contains("The video's Duration must be defined.").contains("The video's Duration format must be correct. (hh:mm)");
    }

    @Test
    void whenDurationDefinedButIncorrectThenValidationFails() {
        var video = new Video("Last Video", "12/20/2010", "12:30", "01:70", "filler");
        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertThat(violations).hasSize(1);
        List<String> constraintViolationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages).contains("The video's Duration format must be correct. (hh:mm)");
    }

    @Test
    void whenLinkNotDefinedThenValidationFails() {
        var video = new Video("Last Video", "12/20/2010", "12:30", "01:00", "");
        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertThat(violations).hasSize(1);
        List<String> constraintViolationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages).contains("The video's Link must be defined.");
    }
}
