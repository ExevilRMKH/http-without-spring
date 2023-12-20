package org.example.repository;

import org.example.domain.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    Optional<Message> findById(String id);
    List<Message> findByAll();
}
