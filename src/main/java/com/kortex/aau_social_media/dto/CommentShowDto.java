package com.kortex.aau_social_media.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentShowDto {
     private String username; // The user who wrote the comment
    private String text; // The actual comment text
    private LocalDateTime createdAt;
}
