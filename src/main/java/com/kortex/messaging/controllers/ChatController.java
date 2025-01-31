package com.kortex.messaging.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.kortex.messaging.dto.ChatMessageDTO;
import com.kortex.messaging.model.Message;
import com.kortex.messaging.repository.MessageRepository;

import java.time.Instant;

@Controller
public class ChatController {

    private final MessageRepository messageRepository;

    public ChatController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // When client sends a message to /app/sendMessage, this method is invoked
  @MessageMapping("/sendMessage")
@SendTo("/topic/group")
public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO) {
    Message newMessage = new Message();
    newMessage.setUsername(chatMessageDTO.getUsername());
    newMessage.setContent(chatMessageDTO.getContent());
    newMessage.setTimestamp(Instant.now());
    messageRepository.save(newMessage);

    // Return DTO with epoch millis
    chatMessageDTO.setTimestamp(newMessage.getTimestamp().toEpochMilli());
    return chatMessageDTO;
}
}
