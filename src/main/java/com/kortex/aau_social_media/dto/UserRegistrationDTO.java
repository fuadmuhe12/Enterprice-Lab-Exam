package com.kortex.aau_social_media.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can be up to 100 characters")
    private String name;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username can be up to 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "University ID is required")
    private String universityId;

    @NotBlank(message = "University Password is required")
    private String universityPassword;
}
