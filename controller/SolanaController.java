package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.SolanaRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.SolanaRecommendationRepository;
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
@RequestMapping("/solana")
public class SolanaController {

    @Autowired
    private SolanaRecommendationRepository solanaRepo;

    @Autowired
    private CommentRepository commentRepo;
    
    

    
    @GetMapping
    public String showCryptoRecommendations(Model model) {
        List<SolanaRecommendation> cryptos = solanaRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", cryptos);
        return "solana"; 
    }

    @GetMapping("/new")
    public String newCryptoForm(Model model) {
        model.addAttribute("cryptoRecommendation", new SolanaRecommendation());
        return "solanaForm"; 
    }
    
    @GetMapping("/{id}")
    public String viewSolana(@PathVariable Long id, Model model) {
        SolanaRecommendation solana = solanaRepo.findById(id).orElse(null);
        if (solana == null) return "redirect:/solana";

        model.addAttribute("solana", solana);
        model.addAttribute("comments", commentRepo.findBySolanaRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());

        return "solanaDetail"; 
    }


    @PostMapping("/{id}/comments")
    public String postCommentOnSolana(@PathVariable Long id,
                                       @ModelAttribute Comment comment,
                                       @AuthenticationPrincipal OAuth2User principal) {
        comment.setSolanaRecommendationId(id);
        comment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
            comment.setAuthor(principal.getAttribute("email"));
        }

        commentRepo.save(comment);
        return "redirect:/solana/" + id;
    }


    @PostMapping("/submit")
    public String submitCrypto(@ModelAttribute SolanaRecommendation solana,
                               @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            solana.setSubmittedBy(principal.getAttribute("email"));
        }
        solanaRepo.save(solana);
        return "redirect:/solana";
    }
}
