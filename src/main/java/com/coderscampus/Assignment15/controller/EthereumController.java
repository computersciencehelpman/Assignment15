package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.EthereumRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.EthereumRecommendationRepository;
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
@RequestMapping("/ethereum")
public class EthereumController {

    @Autowired
    private EthereumRecommendationRepository ethereumRepo;

    @Autowired
    private CommentRepository commentRepo;
    
    @GetMapping
    public String showCryptoRecommendations(Model model) {
        List<EthereumRecommendation> cryptos = ethereumRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", cryptos);
        return "ethereum";
    }

    @GetMapping("/new")
    public String newCryptoForm(Model model) {
        model.addAttribute("cryptoRecommendation", new EthereumRecommendation());
        return "ethereumForm"; 
    }
    
    @GetMapping("/{id}")
    public String viewEthereum(@PathVariable Long id, Model model) {
        EthereumRecommendation ethereum = ethereumRepo.findById(id).orElse(null);
        if (ethereum == null) return "redirect:/ethereum";

        model.addAttribute("ethereum", ethereum); 
        model.addAttribute("comments", commentRepo.findByEthereumRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());

        return "ethereumDetail";
    }

    @PostMapping("/{id}/comments")
    public String postCommentOnEthereum(@PathVariable Long id,
                                       @ModelAttribute Comment newComment,
                                       @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setEthereumRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/ethereum/" + id;
    }


    @PostMapping("/submit")
    public String submitCrypto(@ModelAttribute EthereumRecommendation ethereum,
                               @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            ethereum.setSubmittedBy(principal.getAttribute("email"));
        }
        ethereumRepo.save(ethereum);
        return "redirect:/ethereum";
    }
}
