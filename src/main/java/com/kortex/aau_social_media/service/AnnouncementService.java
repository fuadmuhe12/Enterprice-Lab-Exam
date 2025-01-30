package com.kortex.aau_social_media.service;

import com.kortex.aau_social_media.dto.AnnouncementDTO;
import com.kortex.aau_social_media.dto.AnnouncementWithCommentsDTO;
import com.kortex.aau_social_media.dto.CommentShowDto;
import com.kortex.aau_social_media.model.Announcement;
import com.kortex.aau_social_media.repository.AnnouncementRepository;
import com.kortex.aau_social_media.repository.CommentRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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

    public AnnouncementService(AnnouncementRepository announcementRepository, 
                              CommentRepository commentRepository, FileUpload fileUpload) {
        this.announcementRepository = announcementRepository;
        this.commentRepository = commentRepository;
        this.fileUpload = fileUpload;
        

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
                        getCommentsForAnnouncement(announcement.getId())
                ))
                .collect(Collectors.toList());
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
}