package ru.alt.cloudstorage.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.alt.cloudstorage.exception.ErrorInputData;
import ru.alt.cloudstorage.exception.ErrorInternal;
import ru.alt.cloudstorage.exception.ErrorUnauthorized;

@RestControllerAdvice

public class ExceptionHandlerAdvice {

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<String> eidHandler(ErrorInputData e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorUnauthorized.class)
    public ResponseEntity<String> etHandler(ErrorUnauthorized e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ErrorInternal.class)
    public ResponseEntity<String> etHandler(ErrorInternal e) {
        return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

