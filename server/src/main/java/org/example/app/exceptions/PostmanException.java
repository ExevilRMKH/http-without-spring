package org.example.app.exceptions;

public class PostmanException extends RuntimeException{
    public PostmanException(String message, Throwable cause) {
        super(message, cause);
    }
}
