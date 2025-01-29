package com.kortex.aau_social_media.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String profilePic = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    private String universityId;
    private String universityPassword;

    private String password;

    @Column(unique = true, nullable = false)
    private String username;
}
