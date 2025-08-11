package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.BitcoinNftRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.BitcoinNftRepository;
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
@RequestMapping("/bitcoinnft")
public class BitcoinNftController {

    @Autowired
    private BitcoinNftRepository bitcoinNftRepo;
    
    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAllNfts(Model model) {
        List<BitcoinNftRecommendation> nfts = bitcoinNftRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("nftRecommendations", nfts);
        return "bitcoinnft"; 
    }

    @GetMapping("/new")
    public String newNftForm(Model model) {
        model.addAttribute("bitcoinNftRecommendation", new BitcoinNftRecommendation());
        return "bitcoinnftForm"; 
    }

    @GetMapping("/{id}")
    public String viewNftDetail(@PathVariable Long id, Model model) {
        BitcoinNftRecommendation nft = bitcoinNftRepo.findById(id).orElse(null);
        if (nft == null) return "redirect:/bitcoinnft";

        model.addAttribute("nft", nft);
        model.addAttribute("comments", commentRepo.findByBitcoinNftRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "bitcoinnftDetail";
    }

    
    @PostMapping("/{id}/comments")
    public String postCommentOnBitcoinNft(@PathVariable Long id,
                                          @ModelAttribute Comment newComment,
                                          @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setBitcoinNftRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/bitcoinnft/" + id; 
    }

    @PostMapping("/submit")
    public String submitNft(@ModelAttribute BitcoinNftRecommendation nft,
                            @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            nft.setSubmittedBy(principal.getAttribute("email"));
        }
        nft.setCreatedAt(LocalDateTime.now());
        bitcoinNftRepo.save(nft);
        return "redirect:/bitcoinnft"; 
    }

}

