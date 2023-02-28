package com.project.hb.controller;

import com.project.hb.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<String> handleUserAlreadyExistException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotExistException.class)
    public final ResponseEntity<String> handleUserNotExistException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StayNotExistException.class)
    public final ResponseEntity<String> handleStayNotExistException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(S3UploadException.class)
    public final ResponseEntity<String> handleS3UploadException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidSearchDateException.class)
    public final ResponseEntity<String> handleInvalidSearchDateException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeoCodingException.class)
    public final ResponseEntity<String> handleGeoCodingException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidStayAddressException.class)
    public final ResponseEntity<String> handleInvalidStayAddressException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationCollisionException.class)
    public final ResponseEntity<String> handleReservationCollisionException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public final ResponseEntity<String> handleReservationNotFoundException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidReservationDateException.class)
    public final ResponseEntity<String> handleInvalidReservationDateException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StayDeleteException.class)
    public final ResponseEntity<String> handleStayDeleteException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
