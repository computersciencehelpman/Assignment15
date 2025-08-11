package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.CollectableRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.CollectableRepository;
import com.coderscampus.Assignment15.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/collectables")
public class CollectablesController {

    @Autowired
    private CollectableRepository collectableRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAll(Model model) {
        List<CollectableRecommendation> items = collectableRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", items);
        return "collectables";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("collectable", new CollectableRecommendation());
        return "collectablesForm";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute CollectableRecommendation rec,
                         @AuthenticationPrincipal OAuth2User principal) {
        rec.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            rec.setSubmittedBy(principal.getAttribute("email"));
        }
        collectableRepo.save(rec);
        return "redirect:/collectables";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        CollectableRecommendation rec = collectableRepo.findById(id).orElse(null);
        if (rec == null) return "redirect:/collectables";

        model.addAttribute("collectable", rec);
        model.addAttribute("comments", commentRepo.findByCollectableRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "collectablesDetail";
    }

    @PostMapping("/{id}/comments")
    public String postComment(@PathVariable Long id,
                              @ModelAttribute Comment newComment,
                              @AuthenticationPrincipal OAuth2User principal) {
        newComment.setCollectableRecommendationId(id);
        newComment.setCreatedAt(LocalDateTime.now());
        if (principal != null) {
            newComment.setAuthor(principal.getAttribute("email"));
        }
        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/collectables/" + id;
    }
}
