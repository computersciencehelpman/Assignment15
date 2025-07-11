package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.OtherNftRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.OtherNftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/othernftblockchains")
public class OtherNftController {

    @Autowired
    private OtherNftRepository otherNftRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAllRecommendations(Model model) {
        List<OtherNftRecommendation> nfts = otherNftRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", nfts);
        return "othernftblockchains";
    }

    @GetMapping("/new")
    public String newNftForm(Model model) {
        model.addAttribute("otherNftRecommendation", new OtherNftRecommendation());
        return "othernftblockchainsForm";
    }

    @PostMapping("/submit")
    public String submitNft(@ModelAttribute OtherNftRecommendation nft,
                            @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            nft.setSubmittedBy(principal.getAttribute("email"));
        }
        nft.setCreatedAt(LocalDateTime.now());
        otherNftRepo.save(nft);
        return "redirect:/othernftblockchains";
    }

    @GetMapping("/{id}")
    public String viewNft(@PathVariable Long id, Model model) {
        OtherNftRecommendation nft = otherNftRepo.findById(id).orElse(null);
        if (nft == null) return "redirect:/othernftblockchains";

        model.addAttribute("nft", nft);
        model.addAttribute("comments", commentRepo.findByOtherNftBlockchainsRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "othernftblockchainsDetail";
    }

    @PostMapping("/{id}/comments")
    public String postCommentOnOtherNft(@PathVariable Long id,
                                        @ModelAttribute Comment comment,
                                        @AuthenticationPrincipal OAuth2User principal) {
        comment.setOtherBlockchainsRecommendationId(id);
        comment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
            comment.setAuthor(principal.getAttribute("email"));
        }

        commentRepo.save(comment);
        return "redirect:/othernftblockchains/" + id;
    }
}
