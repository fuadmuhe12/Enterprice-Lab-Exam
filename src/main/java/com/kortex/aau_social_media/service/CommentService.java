package com.kortex.aau_social_media.service;

import com.kortex.aau_social_media.dto.CommentDTO;
import com.kortex.aau_social_media.dto.CommentShowDto;
import com.kortex.aau_social_media.model.Announcement;
import com.kortex.aau_social_media.model.Comment;
import com.kortex.aau_social_media.repository.AnnouncementRepository;
import com.kortex.aau_social_media.repository.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AnnouncementRepository announcementRepository;

    public CommentService(CommentRepository commentRepository,
            AnnouncementRepository announcementRepository) {
        this.commentRepository = commentRepository;
        this.announcementRepository = announcementRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<CommentShowDto> getCommentsForAnnouncement(Long announcementId) {
        return commentRepository.findByAnnouncementIdOrderByCreatedAtDesc(announcementId)
                .stream()
                .map(comment -> new CommentShowDto(
                        comment.getText(),
                        comment.getUsername(),
                        comment.getCreatedAt()))
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