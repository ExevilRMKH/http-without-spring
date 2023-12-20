package org.example.domain;

import org.example.config.exceptions.NotFoundException;

public interface Postman {
    byte[] getMessageById(String id) throws NotFoundException;
    byte[] getMessages();
}
