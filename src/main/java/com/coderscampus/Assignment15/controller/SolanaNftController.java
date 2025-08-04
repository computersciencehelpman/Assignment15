package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.SolanaNftRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.SolanaNftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/solananft")
public class SolanaNftController {

    @Autowired
    private SolanaNftRepository solanaNftRepo;
    
    @Autowired
    private CommentRepository commentRepo;


    @GetMapping
    public String showAllNfts(Model model) {
        List<SolanaNftRecommendation> nfts = solanaNftRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("nftRecommendations", nfts);
        return "solananft";
    }

    @GetMapping("/new")
    public String newNftForm(Model model) {
        model.addAttribute("solanaNftRecommendation", new SolanaNftRecommendation());
        return "solananftForm";
    }

    @PostMapping("/submit")
    public String submitNft(@ModelAttribute SolanaNftRecommendation nft,
                            @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            nft.setSubmittedBy(principal.getAttribute("email"));
        }
        nft.setCreatedAt(LocalDateTime.now());
        solanaNftRepo.save(nft);  // <-- check this
        return "redirect:/solananft";
    }



    @GetMapping("/{id}")
    public String viewNft(@PathVariable Long id, Model model) {
        SolanaNftRecommendation nft = solanaNftRepo.findById(id).orElse(null);
        if (nft == null) return "redirect:/solananft";

        model.addAttribute("nft", nft); // ✅ required for Thymeleaf to render 'nft.submittedBy'
        model.addAttribute("comments", commentRepo.findBySolanaRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "solananftDetail";
    }

    
    @PostMapping("/{id}/comments")
    public String postCommentOnSolanaNft(@PathVariable Long id,
                                           @ModelAttribute Comment newComment,
                                           @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setSolanaRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/solananft/" + id;
    }


}
