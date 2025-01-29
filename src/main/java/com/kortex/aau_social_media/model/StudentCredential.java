package com.kortex.aau_social_media.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCredential {
    private String universityId;
    private String universityPassword;
}
