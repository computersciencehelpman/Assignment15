package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.LandRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.LandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/land")
public class LandController {

    @Autowired
    private LandRepository landRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAll(Model model) {
        List<LandRecommendation> lands = landRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", lands);
        return "land"; 
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("landRecommendation", new LandRecommendation());
        return "landForm";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute LandRecommendation land,
                         @AuthenticationPrincipal OAuth2User principal) {
        land.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            land.setSubmittedBy(principal.getAttribute("email"));
        }
        landRepo.save(land);
        return "redirect:/land";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        LandRecommendation land = landRepo.findById(id).orElse(null);
        if (land == null) return "redirect:/land";

        model.addAttribute("land", land);
        model.addAttribute("comments", commentRepo.findByLandRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "landDetail";
    }

    @PostMapping("/{id}/comments")
    public String postComment(@PathVariable Long id,
                              @ModelAttribute Comment comment,
                              @AuthenticationPrincipal OAuth2User principal) {
        comment.setLandRecommendationId(id);
        comment.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            comment.setAuthor(principal.getAttribute("email"));
        }
        commentRepo.save(comment);
        return "redirect:/land/" + id;
    }
}
