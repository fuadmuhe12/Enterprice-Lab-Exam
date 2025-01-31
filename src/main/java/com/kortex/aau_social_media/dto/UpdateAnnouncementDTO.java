package com.kortex.aau_social_media.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateAnnouncementDTO {
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 2000, message = "Content must be less than 2000 characters")
    private String content;

    private MultipartFile imageFile;

    private String existingImageUrl; // To track existing image
}