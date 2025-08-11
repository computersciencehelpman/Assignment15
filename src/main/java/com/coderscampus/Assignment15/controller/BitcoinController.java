package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.BitcoinRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.BitcoinRecommendationRepository;
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
@RequestMapping("/bitcoin")
public class BitcoinController {

    @Autowired
    private BitcoinRecommendationRepository bitcoinRepo;

    @Autowired
    private CommentRepository commentRepo;
    
    

    
    @GetMapping
    public String showCryptoRecommendations(Model model) {
        List<BitcoinRecommendation> cryptos = bitcoinRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", cryptos);
        return "bitcoin"; // src/main/resources/templates/cryptoList.html
    }

    @GetMapping("/new")
    public String newCryptoForm(Model model) {
        model.addAttribute("cryptoRecommendation", new BitcoinRecommendation());
        return "bitcoinForm"; // matches bitcoinForm.html
    }
    
    @GetMapping("/{id}")
    public String viewBitcoin(@PathVariable Long id, Model model) {
        BitcoinRecommendation bitcoin = bitcoinRepo.findById(id).orElse(null);
        if (bitcoin == null) return "redirect:/bitcoin";

        model.addAttribute("bitcoin", bitcoin);
        model.addAttribute("comments", commentRepo.findByBitcoinRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());

        return "bitcoinDetail"; 
    }


    @PostMapping("/{id}/comments")
    public String postCommentOnBitcoin(@PathVariable Long id,
                                       @ModelAttribute Comment newComment,
                                       @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setBitcoinRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }

        
        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/bitcoin/" + id;
    }


    @PostMapping("/submit")
    public String submitCrypto(@ModelAttribute BitcoinRecommendation bitcoin,
                               @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            bitcoin.setSubmittedBy(principal.getAttribute("email"));
        }
        bitcoinRepo.save(bitcoin);
        return "redirect:/bitcoin";
    }
}
