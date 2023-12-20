package org.example.app.exceptions;

import java.io.Serializable;

public class NotFoundException extends RuntimeException implements Serializable {
    public NotFoundException(String message) {
        super(message);
    }
}
