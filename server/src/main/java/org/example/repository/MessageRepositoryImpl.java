package org.example.repository;

import org.example.domain.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MessageRepositoryImpl implements MessageRepository{

    private static final Map<String, Message> MESSAGE_MAP = new HashMap<>(){};

    static {
        MESSAGE_MAP.put("1", new Message("This simple text 1 if you understand what i mean","testovich1"));
        MESSAGE_MAP.put("2", new Message("This simple text 2 if you understand what i mean","testovich2"));
        MESSAGE_MAP.put("3", new Message("This simple text 3 if you understand what i mean","testovich3"));
    }

    @Override
    public Optional<Message> findById(String id) {
        return Optional.ofNullable(MESSAGE_MAP.get(id));
    }

    @Override
    public List<Message> findByAll() {
        return MESSAGE_MAP
                .values()
                .stream()
                .toList();
    }
}
