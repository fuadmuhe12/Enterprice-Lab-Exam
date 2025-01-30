package com.kortex.aau_social_media.controllers;

import com.kortex.aau_social_media.dto.AnnouncementDTO;
import com.kortex.aau_social_media.dto.AnnouncementWithCommentsDTO;
import com.kortex.aau_social_media.dto.CommentDTO;
import com.kortex.aau_social_media.service.AnnouncementService;
import com.kortex.aau_social_media.service.CommentService;
import com.kortex.aau_social_media.service.FileUpload;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final CommentService commentService;
    private final FileUpload fileUpload;

    public AnnouncementController(AnnouncementService announcementService,
                                 CommentService commentService, FileUpload fileUpload) {
        this.announcementService = announcementService;
        this.commentService = commentService;
        this.fileUpload = fileUpload;
    }

    @GetMapping
    public String showAnnouncements(Model model, @AuthenticationPrincipal Object principal) {
        List<AnnouncementWithCommentsDTO> announcements = announcementService.getAllAnnouncementsWithComments();
        model.addAttribute("announcements", announcements);
        model.addAttribute("announcementDTO", new AnnouncementDTO());
        model.addAttribute("commentDTO", new CommentDTO()); // Add this line
        return "announcements/list";
    }

   

    @ModelAttribute("commentService")
    public CommentService commentService() {
        return commentService;
    }
   
    @PostMapping
    public String createAnnouncement(
            @Valid @ModelAttribute("announcementDTO") AnnouncementDTO announcementDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("announcements", announcementService.getAllAnnouncements());
            return "announcements/list";
        }

        announcementService.createAnnouncement(announcementDTO);
        return "redirect:/announcements";
    }

   
    @PostMapping("/{id}/comments")
    public String createComment(@PathVariable Long id,
            @Valid @ModelAttribute("commentDTO") CommentDTO commentDTO,
            BindingResult result,
            @AuthenticationPrincipal String username,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("announcements", announcementService.getAllAnnouncementsWithComments());
            return "announcements/list";
        }
        commentService.createComment(id, commentDTO, username);
        return "redirect:/announcements";
    }
    

@PostMapping("/{id}/like")
public String toggleLike(@PathVariable Long id, 
                         @AuthenticationPrincipal Object principal) {
    String username;

    if (principal instanceof UserDetails) {
        username = ((UserDetails) principal).getUsername();
    } else {
        throw new RuntimeException("User not authenticated properly");
    }

    announcementService.toggleLike(id, username);
    return "redirect:/announcements";
}

}