package demo.infrastructure.api.exception;

import demo.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(
                buildError(ex),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(DomainException ex) {
        return new ResponseEntity<>(
                buildError(ex),
                HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildError(Exception ex) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .build();
    }
}