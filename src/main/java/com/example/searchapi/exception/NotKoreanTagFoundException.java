package com.example.searchapi.exception;

public class NotKoreanTagFoundException extends RuntimeException{
    public NotKoreanTagFoundException() {
    }

    public NotKoreanTagFoundException(String message) {
        super(message);
    }

    public NotKoreanTagFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotKoreanTagFoundException(Throwable cause) {
        super(cause);
    }
}
