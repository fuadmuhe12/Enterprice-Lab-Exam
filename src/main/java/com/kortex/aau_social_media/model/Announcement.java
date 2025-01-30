package com.kortex.aau_social_media.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    // A set of user names or IDs that liked the announcement
    @ElementCollection
    @CollectionTable(name = "announcement_likes", joinColumns = @JoinColumn(name = "announcement_id"))
    @Column(name = "username")
    private Set<String> likedBy = new HashSet<>();

    // Bi-directional or uni-directional relationship with comments
    // If you prefer storing comments separately, you can omit mappedBy and store in
    // a separate table.
    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

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
