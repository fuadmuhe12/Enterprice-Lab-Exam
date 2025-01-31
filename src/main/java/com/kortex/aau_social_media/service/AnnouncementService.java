package com.kortex.aau_social_media.service;

import com.kortex.aau_social_media.dto.AnnouncementDTO;
import com.kortex.aau_social_media.dto.AnnouncementWithCommentsDTO;
import com.kortex.aau_social_media.dto.CommentShowDto;
import com.kortex.aau_social_media.dto.UpdateAnnouncementDTO;
import com.kortex.aau_social_media.model.Announcement;
import com.kortex.aau_social_media.repository.AnnouncementRepository;
import com.kortex.aau_social_media.repository.CommentRepository;
import com.kortex.aau_social_media.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final CommentRepository commentRepository;
    private final FileUpload fileUpload;
    private final UserRepository userRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository,
            CommentRepository commentRepository, FileUpload fileUpload, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.commentRepository = commentRepository;
        this.fileUpload = fileUpload;
        this.userRepository = userRepository;

    }

    public List<AnnouncementWithCommentsDTO> getAllAnnouncementsWithComments() {
        return announcementRepository.findAll()
                .stream()
                .map(announcement -> new AnnouncementWithCommentsDTO(
                        announcement.getId(),
                        announcement.getTitle(),
                        announcement.getContent(),
                        announcement.getCreatedBy(),
                        announcement.getCreatedAt(),
                        announcement.getLikedBy(),
                        announcement.getImageUrl(),
                        getCommentsForAnnouncement(announcement.getId())))
                .collect(Collectors.toList());
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
                    dto.setId(comment.getId());

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

    public Announcement createAnnouncement(AnnouncementDTO announcementDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Create new Announcement from DTO
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setCreatedBy(username);

        // Check if an image was provided
        MultipartFile imageFile = announcementDTO.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Upload file to Cloudinary and get the URL
                String imageUrl = fileUpload.uploadFile(imageFile);
                announcement.setImageUrl(imageUrl);
            } catch (IOException e) {
                // handle error accordingly, e.g. log it or rethrow
                e.printStackTrace();
            }
        }

        // Save to DB
        return announcementRepository.save(announcement);
    }

    // In AnnouncementService
    public Announcement updateAnnouncement(UpdateAnnouncementDTO updateDTO) {
        Announcement announcement = getAnnouncementById(updateDTO.getId());

        announcement.setTitle(updateDTO.getTitle());
        announcement.setContent(updateDTO.getContent());

        // Handle image update
        MultipartFile newImage = updateDTO.getImageFile();
        if (newImage != null && !newImage.isEmpty()) {
            try {
                String imageUrl = fileUpload.uploadFile(newImage);
                announcement.setImageUrl(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Keep existing image if no new image uploaded
            announcement.setImageUrl(updateDTO.getExistingImageUrl());
        }

        return announcementRepository.save(announcement);
    }
    
    @Transactional
    public void deleteAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        // Clear relationships first
        announcement.getComments().clear();
        announcement.getLikedBy().clear();

        // Save cleared state before deletion
        announcementRepository.save(announcement);

        // Now delete
        announcementRepository.delete(announcement);
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));
    }

    public void toggleLike(Long announcementId, String username) {
        Announcement announcement = getAnnouncementById(announcementId);
        Set<String> likes = announcement.getLikedBy();

        if (likes.contains(username)) {
            likes.remove(username);
        } else {
            likes.add(username);
        }

        announcementRepository.save(announcement);
    }

    public void clearLikes(Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        announcement.getLikedBy().clear();
        announcementRepository.save(announcement);
    }
}

