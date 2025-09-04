package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.OtherBlockchainsRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.OtherBlockchainsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/other-chains")
public class OtherBlockchainController {

    @Autowired private OtherBlockchainsRepository otherBlockchainRepo;
    @Autowired private CommentRepository commentRepo;

    @GetMapping
    public String showCryptoRecommendations(Model model) {
        List<OtherBlockchainsRecommendation> cryptos =
                otherBlockchainRepo.findAllByOrderByCreatedAtDesc();
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
        OtherBlockchainsRecommendation otherBlockchain = otherBlockchainRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("otherBlockchain", otherBlockchain);
        model.addAttribute("comments",
                commentRepo.findByOtherBlockchainsRecommendationIdOrderByCreatedAtDesc(id));
        model.addAttribute("newComment", new Comment()); // matches form
        return "otherBlockchainDetail";
    }

    @PostMapping("/{id}/comments")
    public String postCommentOnOtherBlockchain(@PathVariable Long id,
                                               @ModelAttribute("newComment") Comment newComment,
                                               @AuthenticationPrincipal Object principal) {
        OtherBlockchainsRecommendation rec = otherBlockchainRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        newComment.setId(null);
        newComment.setCreatedAt(LocalDateTime.now());

        newComment.setOtherBlockchainsRecommendationId(rec.getId());

        if (principal instanceof OAuth2User ou) {
            newComment.setAuthor(ou.getAttribute("email"));
        } else if (principal instanceof UserDetails ud) {
            newComment.setAuthor(ud.getUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        commentRepo.save(newComment);
        return "redirect:/other-chains/" + id;
    }

    @PostMapping("/submit")
    public String submitCrypto(@ModelAttribute OtherBlockchainsRecommendation otherBlockchain,
                               @AuthenticationPrincipal Object principal) {
        if (principal instanceof OAuth2User ou) {
            otherBlockchain.setSubmittedBy(ou.getAttribute("email"));
        } else if (principal instanceof UserDetails ud) {
            otherBlockchain.setSubmittedBy(ud.getUsername());
        }
        otherBlockchainRepo.save(otherBlockchain);
        return "redirect:/other-chains";
    }
}
