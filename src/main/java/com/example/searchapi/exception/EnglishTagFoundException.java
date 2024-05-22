package com.example.searchapi.exception;

public class EnglishTagFoundException extends RuntimeException{
    public EnglishTagFoundException() {
    }

    public EnglishTagFoundException(String message) {
        super(message);
    }

    public EnglishTagFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnglishTagFoundException(Throwable cause) {
        super(cause);
    }
}
