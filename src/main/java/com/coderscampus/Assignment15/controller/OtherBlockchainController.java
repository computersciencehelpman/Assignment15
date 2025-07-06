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

    // List all Other Blockchain Recommendations
    @GetMapping
    public String showCryptoRecommendations(Model model) {
        List<OtherBlockchainsRecommendation> cryptos = otherBlockchainRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("recommendations", cryptos);
        return "otherblockchains"; // Make sure this matches the template filename exactly
    }

    // Show form to create a new recommendation
    @GetMapping("/new")
    public String newCryptoForm(Model model) {
        model.addAttribute("cryptoRecommendation", new OtherBlockchainsRecommendation());
        return "otherBlockchainForm"; // Matches otherBlockchainForm.html
    }

    // View detail of a specific recommendation
    @GetMapping("/{id}")
    public String viewOtherBlockchain(@PathVariable Long id, Model model) {
        OtherBlockchainsRecommendation otherBlockchain = otherBlockchainRepo.findById(id).orElse(null);
        if (otherBlockchain == null) return "redirect:/other-chains";

        model.addAttribute("otherBlockchain", otherBlockchain);
        model.addAttribute("comments", commentRepo.findByOtherBlockchainsRecommendationIdOrderByCreatedAtDesc(id));
        Comment freshComment = new Comment();
        freshComment.setId(null); // Ensure ID is null so Hibernate treats it as NEW
        model.addAttribute("newComment", freshComment);



        return "otherBlockchainDetail"; // Matches otherBlockchainDetail.html
    }

    // Post a new comment
    @PostMapping("/{id}/comments")
    public String postCommentOnOtherBlockchain(@PathVariable Long id,
                                               @ModelAttribute Comment comment,
                                               @AuthenticationPrincipal OAuth2User principal) {
        comment.setId(null); // ✅ This is the fix
        comment.setOtherBlockchainsRecommendationId(id);
        comment.setCreatedAt(LocalDateTime.now());

        if (principal != null) {
            comment.setAuthor(principal.getAttribute("email"));
        }

        commentRepo.save(comment);
        return "redirect:/other-chains/" + id;
    }


    // Handle form submission of new recommendation
    @PostMapping("/submit")
    public String submitCrypto(@ModelAttribute OtherBlockchainsRecommendation otherBlockchain,
                               @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            otherBlockchain.setSubmittedBy(principal.getAttribute("email"));
        }
        otherBlockchainRepo.save(otherBlockchain);
        return "redirect:/other-chains"; // ✅ Correct redirect to refresh the list
    }
}
