package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.CardanoNftRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.CardanoNftRepository;
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
@RequestMapping("/cardanonft")
public class CardanoNftController {

    @Autowired
    private CardanoNftRepository cardanoNftRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String showAllNfts(Model model) {
        List<CardanoNftRecommendation> nfts = cardanoNftRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("nftRecommendations", nfts);
        return "cardanonft";
    }

    @GetMapping("/new")
    public String newNftForm(Model model) {
        model.addAttribute("cardanoNftRecommendation", new CardanoNftRecommendation());
        return "cardanonftForm";
    }

    @PostMapping("/submit")
    public String submitNft(@ModelAttribute CardanoNftRecommendation nft,
                            @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            nft.setSubmittedBy(principal.getAttribute("email"));
        }
        nft.setCreatedAt(LocalDateTime.now());
        cardanoNftRepo.save(nft);
        return "redirect:/cardanonft";
    }

    @GetMapping("/{id}")
    public String viewNft(@PathVariable Long id, Model model) {
        CardanoNftRecommendation nft = cardanoNftRepo.findById(id).orElse(null);
        if (nft == null) return "redirect:/cardanonft";

        model.addAttribute("nft", nft);
        model.addAttribute("comments", commentRepo.findByCardanoRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment());
        return "cardanonftDetail";
    }

    @PostMapping("/{id}/comments")
    public String postCommentOnCardanoNft(@PathVariable Long id,
                                          @ModelAttribute Comment comment,
                                          @AuthenticationPrincipal OAuth2User principal) {
        comment.setCardanoRecommendationId(id);
        comment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
            comment.setAuthor(principal.getAttribute("email"));
        }

        commentRepo.save(comment);
        return "redirect:/cardanonft/" + id;
    }
}
