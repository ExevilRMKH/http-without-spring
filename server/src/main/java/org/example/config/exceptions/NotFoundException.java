package org.example.config.exceptions;

import java.io.Serializable;

public class NotFoundException extends RuntimeException implements Serializable {
    public NotFoundException(String message) {
        super(message);
    }
}
