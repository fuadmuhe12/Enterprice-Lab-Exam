package com.kortex.aau_social_media.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentShowDto {
    private String username; // the user’s 'username' (login)
    private String text; // the actual comment text
    private LocalDateTime createdAt;

    // New fields to display more user info
    private String displayName; // e.g. the user’s "name" from UserEntity
    private String profilePic; // profile picture URL
    private String role; // user’s role
}
