package com.bajdas.shopping.rest;

import com.bajdas.shopping.model.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler({IllegalArgumentException.class,
        MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handle(IllegalArgumentException exception) {
        log.warn("Bad request: {}", exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(exception.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handle(ServiceException exception) {
        log.error("Service exception: {}", exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(exception.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception exception) {
        log.error("Unexpected exception: {}", exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body("Unexpected error");
    }
}
