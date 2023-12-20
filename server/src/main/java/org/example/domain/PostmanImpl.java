package org.example.domain;

import org.example.config.exceptions.NotFoundException;
import org.example.config.http.HttpHelpers;
import org.example.repository.MessageRepository;

public record PostmanImpl(MessageRepository repository) implements Postman{

    @Override
    public byte[] getMessageById(String id) {
        return repository
                .findById(id)
                .map(HttpHelpers::objectToArray)
                .orElseThrow(() -> new NotFoundException("message didn't find by id=" + id));
    }

    @Override
    public byte[] getMessages() {
        return HttpHelpers.objectToArray(repository.findByAll());
    }
}
