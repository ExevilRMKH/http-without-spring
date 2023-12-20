package org.example.config.exceptions;

public class PostmanException extends RuntimeException{
    public PostmanException() {
    }

    public PostmanException(String message) {
        super(message);
    }

    public PostmanException(String message, Throwable cause) {
        super(message, cause);
    }
}
