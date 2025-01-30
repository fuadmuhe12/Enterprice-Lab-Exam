package com.kortex.messaging.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kortex.messaging.model.Message;
import com.kortex.messaging.repository.MessageRepository;

import java.util.List;

/**
 * Exposes a REST endpoint for fetching chat history
 */
@RestController
public class ChatHistoryController {

    private final MessageRepository messageRepository;

    public ChatHistoryController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/api/chat/messages")
    public List<Message> getAllMessages() {
        // Optionally, sort by timestamp or do custom ordering:
        // return messageRepository.findAll(Sort.by("timestamp").ascending());
        return messageRepository.findAll();
    }
}
