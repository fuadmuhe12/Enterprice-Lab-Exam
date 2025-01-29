package com.kortex.aau_social_media.service;

import com.kortex.aau_social_media.dto.UserRegistrationDTO;
import com.kortex.aau_social_media.mapper.UserMapper;
import com.kortex.aau_social_media.model.UserEntity;
import com.kortex.aau_social_media.repository.UserRepository;
import com.kortex.aau_social_media.constants.AauStudents;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(UserRegistrationDTO registrationDTO) {
        if (!AauStudents.userExists(registrationDTO.getUniversityId(), registrationDTO.getUniversityPassword())) {
            return "University Student Does Not exist";
        }

        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            return "Username already used";
        }

        UserEntity user = UserMapper.toEntity(registrationDTO);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(user);

        return null; // No error
    }
}