package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.PolygonRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.PolygonRecommendationRepository;
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
@RequestMapping("/polygon")
public class PolygonController {

    @Autowired
    private PolygonRecommendationRepository polygonRepo;

    @Autowired
    private CommentRepository commentRepo;
    
    

    
    @GetMapping
    public String showCryptoRecommendations(Model model) {
        List<PolygonRecommendation> cryptos = polygonRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", cryptos);
        return "polygon"; 
    }

    @GetMapping("/new")
    public String newCryptoForm(Model model) {
        model.addAttribute("cryptoRecommendation", new PolygonRecommendation());
        return "polygonForm"; 
    }
    
    @GetMapping("/{id}")
    public String viewPolygon(@PathVariable Long id, Model model) {
        PolygonRecommendation polygon = polygonRepo.findById(id).orElse(null);
        if (polygon == null) return "redirect:/polygon";

        model.addAttribute("polygon", polygon);
        model.addAttribute("comments", commentRepo.findByPolygonRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());

        return "polygonDetail"; 
    }


    @PostMapping("/{id}/comments")
    public String postCommentOnPolygon(@PathVariable Long id,
                                       @ModelAttribute Comment newComment,
                                       @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setPolygonRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/polygon/" + id;
    }


    @PostMapping("/submit")
    public String submitCrypto(@ModelAttribute PolygonRecommendation polygon,
                               @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            polygon.setSubmittedBy(principal.getAttribute("email"));
        }
        polygonRepo.save(polygon);
        return "redirect:/polygon";
    }
}
