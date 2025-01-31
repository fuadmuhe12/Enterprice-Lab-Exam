package com.kortex.aau_social_media.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Announcement title
    @Column(length = 2000)
    private String content; // Detailed content

    private LocalDateTime createdAt;

    // Optional: track who created the announcement
    private String createdBy;

    private String imageUrl; // <-- NEW: store the URL of the uploaded image

    // A set of user names or IDs that liked the announcement
    
    @ElementCollection
    @CollectionTable(name = "announcement_likes", joinColumns = @JoinColumn(name = "announcement_id"))
    @Column(name = "username")
    private Set<String> likedBy = new HashSet<>();

    // Add proper cascade configuration for relationships
    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // Add this helper method for bidirectional relationship
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setAnnouncement(this);
    }
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
