package com.example.crudapplication.Exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message ,Throwable cause) {
        super(message,cause);
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
