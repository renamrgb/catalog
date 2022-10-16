package catalog.renamrgb.github.com.catalog.controllers.exceptions;

import catalog.renamrgb.github.com.catalog.services.exceptions.DatabaseException;
import catalog.renamrgb.github.com.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandarError> entityNotFound(ResourceNotFoundException e,
                                                       HttpServletRequest request) {
        StandarError err = StandarErrorFactory(e, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandarError> database(DatabaseException e,
                                                 HttpServletRequest request) {
        StandarError err = StandarErrorFactory(e, request, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    private StandarError StandarErrorFactory(Exception e, HttpServletRequest request, HttpStatus status) {
        StandarError err = new StandarError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(status.getReasonPhrase());
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return err;
    }

}
