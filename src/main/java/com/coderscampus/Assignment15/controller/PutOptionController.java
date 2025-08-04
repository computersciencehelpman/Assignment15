package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.domain.PutOptionRecommendation;
import com.coderscampus.Assignment15.repository.CommentRepository;
import com.coderscampus.Assignment15.repository.PutOptionRecommendationRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/puts")
public class PutOptionController {

    @Autowired
    private PutOptionRecommendationRepository putRepo;

    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String listPuts(Model model) {
        List<PutOptionRecommendation> puts = putRepo.findAllByOrderBySubmittedAtDesc();
        model.addAttribute("putRecommendations", puts); // match Thymeleaf
        return "puts";
    }

    @GetMapping("/new")
    public String showPutForm(Model model) {
        model.addAttribute("putOption", new PutOptionRecommendation());
        return "putForm";
    }

    @PostMapping("/submit")
    public String submitPut(@ModelAttribute PutOptionRecommendation putOption,
                            @AuthenticationPrincipal OAuth2User principal) {
        putOption.setSubmittedAt(LocalDateTime.now());
        if (principal != null) {
            String email = principal.getAttribute("email");
            putOption.setSubmittedBy(email);
        }
        putRepo.save(putOption);
        return "redirect:/puts";
    }

    @GetMapping("/{id}")
    public String viewPut(@PathVariable Long id, Model model) {
        PutOptionRecommendation putOption = putRepo.findById(id).orElse(null);
        if (putOption == null) return "redirect:/puts";

        List<Comment> comments = commentRepo.findByPutOptionIdOrderByCreatedAtDesc(id);
        model.addAttribute("put", putOption);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        return "putDetail";
    }

    @PostMapping("/{id}/comments")
    public String submitPutComment(@PathVariable Long id,
                                   @ModelAttribute Comment newComment,
                                   @AuthenticationPrincipal Object principal) {
        newComment.setPutOptionId(id);
        newComment.setCreatedAt(LocalDateTime.now());

        if (principal instanceof OAuth2User oauthUser) {
            newComment.setAuthor(oauthUser.getAttribute("email"));
        } else if (principal instanceof UserDetails userDetails) {
            newComment.setAuthor(userDetails.getUsername());
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/puts/" + id;
    }
}
