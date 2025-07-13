package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.ReitRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.ReitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reits")
public class ReitController {

    @Autowired
    private ReitRepository reitRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAll(Model model) {
        List<ReitRecommendation> listings = reitRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", listings);
        return "reits"; 
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("reitRecommendation", new ReitRecommendation());
        return "reitForm"; // You’ll need this form page too
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute ReitRecommendation rec,
                         @AuthenticationPrincipal OAuth2User principal) {
        rec.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            rec.setSubmittedBy(principal.getAttribute("email"));
        }
        reitRepo.save(rec);
        return "redirect:/reits";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        ReitRecommendation rec = reitRepo.findById(id).orElse(null);
        if (rec == null) return "redirect:/reits";

        model.addAttribute("reit", rec);
        model.addAttribute("comments", commentRepo.findByReitRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "reitDetail";
    }

    @PostMapping("/{id}/comments")
    public String postComment(@PathVariable Long id,
                              @ModelAttribute Comment comment,
                              @AuthenticationPrincipal OAuth2User principal) {
        comment.setReitRecommendationId(id);
        comment.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            comment.setAuthor(principal.getAttribute("email"));
        }
        commentRepo.save(comment);
        return "redirect:/reits/" + id;
    }
}
