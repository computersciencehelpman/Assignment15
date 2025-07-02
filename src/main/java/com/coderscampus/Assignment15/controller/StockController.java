package com.coderscampus.Assignment15.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.StockRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.StockRecommendationRepository;

@Controller
public class StockController {

    @Autowired
    private StockRecommendationRepository stockRecommendationRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping("/stocks/{id}")
    public String viewRecommendation(@PathVariable Long id, Model model) {
        StockRecommendation recommendation = stockRecommendationRepo.findById(id).orElseThrow();
        List<Comment> comments = commentRepo.findByRecommendationIdOrderByCreatedAtDesc(id);

        model.addAttribute("recommendation", recommendation);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());

        return "stockDetail";
    }

    @PostMapping("/stocks/{id}/comments")
    public String postComment(@PathVariable Long id,
                              @ModelAttribute("newComment") Comment comment,
                              @AuthenticationPrincipal Object principal) {

        comment.setId(null);

        if (principal instanceof OAuth2User oauthUser) {
            comment.setAuthor(oauthUser.getAttribute("email"));
        } else if (principal instanceof UserDetails userDetails) {
            comment.setAuthor(userDetails.getUsername());
        } else {
            return "redirect:/login";
        }

        StockRecommendation recommendation = stockRecommendationRepo.findById(id).orElseThrow();
        comment.setRecommendation(recommendation);
        commentRepo.save(comment);

        return "redirect:/stocks/" + id;
    }

}


