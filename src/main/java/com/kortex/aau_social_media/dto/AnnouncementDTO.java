// AnnouncementDTO.java
package com.kortex.aau_social_media.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 2000, message = "Content must be less than 2000 characters")
    private String content;

}
