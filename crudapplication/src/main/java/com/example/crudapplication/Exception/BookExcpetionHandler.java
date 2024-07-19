package com.example.crudapplication.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExcpetionHandler {
    @ExceptionHandler(value = {BookNotFoundException.class})

    public ResponseEntity<Object> bookNotFound(BookNotFoundException bookNotFoundException) {
        BookException bookException = new BookException(bookNotFoundException.getMessage(),
                bookNotFoundException.getCause(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(bookException, HttpStatus.BAD_REQUEST);
    }
}
