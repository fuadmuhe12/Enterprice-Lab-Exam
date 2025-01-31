package com.kortex.aau_social_media.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 
    private String name;

    private String profilePic = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";

    @Column(name = "role", nullable = false, columnDefinition = "varchar(255) default 'USER'")
    private String role;

    private String universityId;
    private String universityPassword;

    private String password;

    @Column(unique = true, nullable = false)
    private String username; */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
public class UserShowDto {

    private String name;
    private String profilePic;
    private String role;
    private String universityId;
    private String username;
}
