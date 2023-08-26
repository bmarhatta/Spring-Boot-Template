package bishwo.springboot.template.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class Exceptions {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> exceptionHandler(Exception e) {
        String message = e.getMessage();
        log.error("Stack Trace = ", e.getStackTrace());
        log.error("detailed message = ", message);
        if (e instanceof Template500Exception) {
            return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (e instanceof Template400Exception) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
