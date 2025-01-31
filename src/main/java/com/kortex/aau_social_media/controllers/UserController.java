package com.kortex.aau_social_media.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kortex.aau_social_media.dto.UserShowDto;
import com.kortex.aau_social_media.repository.UserRepository;
import com.kortex.aau_social_media.security.CustomUserDetails;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserShowDto> getUser(@PathVariable String username) {

        UserShowDto userShowDto = new UserShowDto();

        userRepository.findByUsername(username).ifPresentOrElse(user -> {
            userShowDto.setName(user.getName());
            userShowDto.setProfilePic(user.getProfilePic());
            userShowDto.setRole(user.getRole());
            userShowDto.setUniversityId(user.getUniversityId());
            userShowDto.setUsername(username);
        }, () -> {
            userShowDto.setName("Deleted User");
            userShowDto.setProfilePic("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
            userShowDto.setRole("N/A");
            userShowDto.setUniversityId("N/A");
            userShowDto.setUsername(username);
        });

        return ResponseEntity.ok(userShowDto);
    }


}
