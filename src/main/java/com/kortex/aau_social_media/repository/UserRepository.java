package com.kortex.aau_social_media.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kortex.aau_social_media.model.UserEntity;

public interface UserRepository  extends JpaRepository<UserEntity, Integer>{
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByUniversityId(String universityId);

    boolean existsByUsername(String username);
    boolean existsByUniversityId(String universityId);
} 
