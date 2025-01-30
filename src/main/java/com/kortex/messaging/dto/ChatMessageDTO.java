package com.kortex.messaging.dto;

public class ChatMessageDTO {
    private String username;
    private String content;

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String username, String content) {
        this.username = username;
        this.content = content;
    }

    // getters & setters

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
