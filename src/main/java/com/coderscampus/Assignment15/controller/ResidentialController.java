package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.ResidentialRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.ResidentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/residential")
public class ResidentialController {

    @Autowired
    private ResidentialRepository residentialRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAll(Model model) {
        List<ResidentialRecommendation> listings = residentialRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", listings);
        return "residential"; 
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("residentialRecommendation", new ResidentialRecommendation());
        return "residentialForm"; // You’ll need this form page too
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute ResidentialRecommendation rec,
                         @AuthenticationPrincipal OAuth2User principal) {
        rec.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            rec.setSubmittedBy(principal.getAttribute("email"));
        }
        residentialRepo.save(rec);
        return "redirect:/residential";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        ResidentialRecommendation rec = residentialRepo.findById(id).orElse(null);
        if (rec == null) return "redirect:/realestate";

        model.addAttribute("realEstate", rec);
        model.addAttribute("comments", commentRepo.findByResidentialRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "residentialDetail";
    }

    @PostMapping("/{id}/comments")
    public String postComment(@PathVariable Long id,
                              @ModelAttribute Comment comment,
                              @AuthenticationPrincipal OAuth2User principal) {
        comment.setResidentialRecommendationId(id);
        comment.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            comment.setAuthor(principal.getAttribute("email"));
        }
        commentRepo.save(comment);
        return "redirect:/residential/" + id;
    }
}
