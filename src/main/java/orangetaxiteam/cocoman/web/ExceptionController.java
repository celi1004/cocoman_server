package orangetaxiteam.cocoman.web;

import orangetaxiteam.cocoman.application.dto.ExceptionDTO;
import orangetaxiteam.cocoman.domain.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice("orangetaxiteam.cocoman.web")
public class ExceptionController {
    // Bad Request - 400
    // Parameter validation : Check whether the parameter is null, blank, etc.
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> BadRequestException(final MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), "Parameter bad request", exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    // Bad Request - 400
    // Parameter validation : Check whether the parameter type has error
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> BadRequestException(final HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), "Parameter type bad request", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Bad Request - 400
    // Input value validation : Check whether the entered value does not return null value from DB.
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> BadRequestException(final BadRequestException exception) {
        return new ResponseEntity<>(exception.getResponse(), HttpStatus.BAD_REQUEST);
    }

    // Access Denied - 401
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}