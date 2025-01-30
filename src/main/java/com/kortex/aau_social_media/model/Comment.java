package com.kortex.aau_social_media.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // The user who wrote the comment
    private String text; // The actual comment text
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
