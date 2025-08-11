package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.CommercialRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.CommercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/commercial")
public class CommercialController {

    @Autowired
    private CommercialRepository commercialRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAll(Model model) {
        List<CommercialRecommendation> listings = commercialRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", listings);
        return "commercial"; 
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("commercialRecommendation", new CommercialRecommendation());
        return "commercialForm";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute CommercialRecommendation rec,
                         @AuthenticationPrincipal OAuth2User principal) {
        rec.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            rec.setSubmittedBy(principal.getAttribute("email"));
        }
        commercialRepo.save(rec);
        return "redirect:/commercial";
    }
    
    @GetMapping("/{id}")
    public String viewCommercial(@PathVariable Long id, Model model) {
        CommercialRecommendation commercial = commercialRepo.findById(id).orElse(null);
        if (commercial == null) return "redirect:/commercial";

        model.addAttribute("commercial", commercial);
        model.addAttribute("comments", commentRepo.findByCommercialRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "commercialDetail";
    }

    @PostMapping("/{id}/comments")
    public String postComment(@PathVariable Long id,
                              @ModelAttribute Comment newComment,
                              @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setCommercialRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }
        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/commercial/" + id;
    }
}
