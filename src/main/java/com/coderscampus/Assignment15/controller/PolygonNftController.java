package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.PolygonNftRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.PolygonNftRepository;
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
@RequestMapping("/polygonnft")
public class PolygonNftController {

    @Autowired
    private PolygonNftRepository polygonNftRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAllNfts(Model model) {
        List<PolygonNftRecommendation> nfts = polygonNftRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("nftRecommendations", nfts);
        return "polygonnft";
    }

    @GetMapping("/new")
    public String newNftForm(Model model) {
        model.addAttribute("polygonNftRecommendation", new PolygonNftRecommendation());
        return "polygonnftForm";
    }

    @PostMapping("/submit")
    public String submitNft(@ModelAttribute PolygonNftRecommendation nft,
                            @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            nft.setSubmittedBy(principal.getAttribute("email"));
        }
        nft.setCreatedAt(LocalDateTime.now());
        polygonNftRepo.save(nft);
        return "redirect:/polygonnft";
    }

    @GetMapping("/{id}")
    public String viewNft(@PathVariable Long id, Model model) {
        PolygonNftRecommendation nft = polygonNftRepo.findById(id).orElse(null);
        if (nft == null) return "redirect:/polygonnft";

        model.addAttribute("nft", nft);
        model.addAttribute("comments", commentRepo.findByPolygonRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "polygonnftDetail";
    }

    @PostMapping("/{id}/comments")
    public String postCommentOnPolygonNft(@PathVariable Long id,
                                          @ModelAttribute Comment newComment,
                                          @AuthenticationPrincipal OAuth2User principal) {
    	newComment.setPolygonRecommendationId(id);
    	newComment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
        	newComment.setAuthor(principal.getAttribute("email"));
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/polygonnft/" + id;
    }
}
