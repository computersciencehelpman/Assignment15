package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.CardanoRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.CardanoRecommendationRepository;
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
@RequestMapping("/cardano")
public class CardanoController {

    @Autowired
    private CardanoRecommendationRepository cardanoRepo;

    @Autowired
    private CommentRepository commentRepo;
    
    

    
    @GetMapping
    public String showCryptoRecommendations(Model model) {
        List<CardanoRecommendation> cryptos = cardanoRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", cryptos);
        return "cardano"; // src/main/resources/templates/cryptoList.html
    }

    @GetMapping("/new")
    public String newCryptoForm(Model model) {
        model.addAttribute("cryptoRecommendation", new CardanoRecommendation());
        return "cardanoForm"; // matches cardanoForm.html
    }
    
    @GetMapping("/{id}")
    public String viewCardano(@PathVariable Long id, Model model) {
        CardanoRecommendation cardano = cardanoRepo.findById(id).orElse(null);
        if (cardano == null) return "redirect:/cardano";

        model.addAttribute("cardano", cardano);
        model.addAttribute("comments", commentRepo.findByCardanoRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());

        return "cardanoDetail"; 
    }


    @PostMapping("/{id}/comments")
    public String postCommentOnCardano(@PathVariable Long id,
                                       @ModelAttribute Comment newComment,
                                       @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setCardanoRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/cardano/" + id;
    }


    @PostMapping("/submit")
    public String submitCrypto(@ModelAttribute CardanoRecommendation cardano,
                               @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            cardano.setSubmittedBy(principal.getAttribute("email"));
        }
        cardanoRepo.save(cardano);
        return "redirect:/cardano";
    }
}
