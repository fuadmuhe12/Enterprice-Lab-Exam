package com.kortex.aau_social_media.controllers;

import com.kortex.aau_social_media.dto.CommentDTO;
import com.kortex.aau_social_media.dto.CommentUpdateDTO;
import com.kortex.aau_social_media.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // CREATE COMMENT (if you want to keep it separate from AnnouncementController)
    @PostMapping("/announcement/{announcementId}")
    public ResponseEntity<?> createComment(
            @PathVariable Long announcementId,
            @RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal UserDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("User not authenticated.");
        }

        String username = principal.getUsername();
        commentService.createComment(announcementId, commentDTO, username);
        return ResponseEntity.ok("Comment created successfully!");
    }

    // UPDATE COMMENT
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDTO updateDTO,
            @AuthenticationPrincipal UserDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("User not authenticated.");
        }

        String username = principal.getUsername();
        // Set the commentId in the DTO if not provided
        updateDTO.setCommentId(commentId);

        try {
            commentService.updateComment(updateDTO, username);
            return ResponseEntity.ok("Comment updated successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE COMMENT
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("User not authenticated.");
        }

        String username = principal.getUsername();

        try {
            commentService.deleteComment(commentId, username);
            return ResponseEntity.ok("Comment deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
