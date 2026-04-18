package p466.team2.scheduleservice.web;

import p466.team2.scheduleservice.domain.VideoAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import p466.team2.scheduleservice.domain.VideoOverlapsWithAnotherException;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handler. Incredibly useful as we can see detailed error reports if something goes wrong.
 * Especially so for any custom exceptions we choose to make.
 */
@RestControllerAdvice
public class ScheduleControllerAdvice {

    @ExceptionHandler(VideoAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    String videoAlreadyExistsHandler(VideoAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(VideoOverlapsWithAnotherException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    String videoOverlapsWithAnotherHandler(VideoOverlapsWithAnotherException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
