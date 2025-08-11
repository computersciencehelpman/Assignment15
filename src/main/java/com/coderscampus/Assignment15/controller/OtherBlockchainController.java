package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.OtherBlockchainsRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.OtherBlockchainsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/other-chains")
public class OtherBlockchainController {

    @Autowired
    private OtherBlockchainsRepository otherBlockchainRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showCryptoRecommendations(Model model) {
        List<OtherBlockchainsRecommendation> cryptos = otherBlockchainRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", cryptos);
        return "otherblockchains"; 
    }

   
    @GetMapping("/new")
    public String newCryptoForm(Model model) {
        model.addAttribute("cryptoRecommendation", new OtherBlockchainsRecommendation());
        return "otherBlockchainForm"; 
    }

    @GetMapping("/{id}")
    public String viewOtherBlockchain(@PathVariable Long id, Model model) {
        OtherBlockchainsRecommendation otherBlockchain = otherBlockchainRepo.findById(id).orElse(null);
        if (otherBlockchain == null) return "redirect:/other-chains";

        model.addAttribute("otherBlockchain", otherBlockchain);
        model.addAttribute("comments", commentRepo.findByOtherBlockchainsRecommendationIdOrderByCreatedAtDesc(id));
        Comment freshComment = new Comment();
        freshComment.setId(null); 
        model.addAttribute("newComment", freshComment);



        return "otherBlockchainDetail"; 
    }

    
    @PostMapping("/{id}/comments")
    public String postCommentOnOtherBlockchain(@PathVariable Long id,
                                               @ModelAttribute Comment newComment,
                                               @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setId(null); 
    	newComment.setOtherBlockchainsRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/other-chains/" + id;
    }


    @PostMapping("/submit")
    public String submitCrypto(@ModelAttribute OtherBlockchainsRecommendation otherBlockchain,
                               @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            otherBlockchain.setSubmittedBy(principal.getAttribute("email"));
        }
        otherBlockchainRepo.save(otherBlockchain);
        return "redirect:/other-chains"; 
    }
}
