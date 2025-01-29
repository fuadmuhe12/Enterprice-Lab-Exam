package com.kortex.aau_social_media.mapper;

import com.kortex.aau_social_media.dto.UserRegistrationDTO;
import com.kortex.aau_social_media.model.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserRegistrationDTO registrationDTO){

        UserEntity user = new UserEntity();

        user.setName(registrationDTO.getName());
        user.setPassword(registrationDTO.getPassword());
        user.setUniversityId(registrationDTO.getUniversityId());
        user.setUniversityPassword(registrationDTO.getUniversityPassword());
        user.setUsername(registrationDTO.getUsername());

        return user;
    }

    
}
