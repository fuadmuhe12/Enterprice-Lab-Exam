package com.kortex.aau_social_media.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDTO {

    private Long commentId; // The ID of the comment we want to update

    @NotBlank(message = "Comment cannot be blank")
    @Size(max = 500, message = "Comment must be less than 500 characters")
    private String newText;
}
