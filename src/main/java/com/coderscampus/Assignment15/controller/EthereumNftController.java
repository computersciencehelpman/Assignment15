package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.EthereumNftRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.EthereumNftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ethereumnft")
public class EthereumNftController {

    @Autowired
    private EthereumNftRepository ethereumNftRepo;
    
    @Autowired
    private CommentRepository commentRepo;


    @GetMapping
    public String showAllNfts(Model model) {
        List<EthereumNftRecommendation> nfts = ethereumNftRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("nftRecommendations", nfts);
        return "ethereumnft";
    }

    @GetMapping("/new")
    public String newNftForm(Model model) {
        model.addAttribute("ethereumNftRecommendation", new EthereumNftRecommendation());
        return "ethereumnftForm";
    }

    @PostMapping("/submit")
    public String submitNft(@ModelAttribute EthereumNftRecommendation nft,
                            @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            nft.setSubmittedBy(principal.getAttribute("email")); // ✅ ensures 'submittedBy' is saved
        }
        nft.setCreatedAt(LocalDateTime.now()); // ✅ ensures 'createdAt' is saved
        ethereumNftRepo.save(nft);
        return "redirect:/ethereumnft";
    }


    @GetMapping("/{id}")
    public String viewNft(@PathVariable Long id, Model model) {
        EthereumNftRecommendation nft = ethereumNftRepo.findById(id).orElse(null);
        if (nft == null) return "redirect:/ethereumnft";

        model.addAttribute("nft", nft); // ✅ required for Thymeleaf to render 'nft.submittedBy'
        model.addAttribute("comments", commentRepo.findByEthereumRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "ethereumnftDetail";
    }

    
    @PostMapping("/{id}/comments")
    public String postCommentOnEthereumNft(@PathVariable Long id,
                                           @ModelAttribute Comment comment,
                                           @AuthenticationPrincipal OAuth2User principal) {
        comment.setEthereumRecommendationId(id);
        comment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
            comment.setAuthor(principal.getAttribute("email"));
        }

        commentRepo.save(comment);
        return "redirect:/ethereumnft/" + id;
    }


}
