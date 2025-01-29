package com.kortex.aau_social_media.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kortex.aau_social_media.model.UserEntity;
import com.kortex.aau_social_media.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
    final private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDetails userDetails = new User(user.getUsername(), user.getPassword(),mapRolesToAuthorities(user.getRoles()));
        return userDetails;

    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
}
