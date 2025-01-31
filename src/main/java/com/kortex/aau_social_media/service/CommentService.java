package com.kortex.aau_social_media.service;

import com.kortex.aau_social_media.dto.CommentDTO;
import com.kortex.aau_social_media.dto.CommentShowDto;
import com.kortex.aau_social_media.model.Announcement;
import com.kortex.aau_social_media.model.Comment;
import com.kortex.aau_social_media.repository.AnnouncementRepository;
import com.kortex.aau_social_media.repository.CommentRepository;
import com.kortex.aau_social_media.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
            AnnouncementRepository announcementRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<CommentShowDto> getCommentsForAnnouncement(Long announcementId) {
        return commentRepository.findByAnnouncementIdOrderByCreatedAtDesc(announcementId)
                .stream()
                .map(comment -> {
                    // Create base DTO
                    CommentShowDto dto = new CommentShowDto();
                    dto.setUsername(comment.getUsername());
                    dto.setText(comment.getText());
                    dto.setCreatedAt(comment.getCreatedAt());

                    // Lookup the user from userRepository
                    userRepository.findByUsername(comment.getUsername())
                            .ifPresentOrElse(user -> {
                                // Populate fields from UserEntity
                                dto.setDisplayName(user.getName());
                                dto.setProfilePic(user.getProfilePic());
                                dto.setRole(user.getRole());
                            }, () -> {
                                // If user not found, you can set some defaults if you want
                                dto.setDisplayName("Unknown User");
                                dto.setProfilePic("https://example.com/default-profile.png");
                                dto.setRole("N/A");
                            });

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void createComment(Long announcementId, CommentDTO commentDTO, String username) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setUsername(username);
        comment.setAnnouncement(announcement);
        commentRepository.save(comment);
    }
}