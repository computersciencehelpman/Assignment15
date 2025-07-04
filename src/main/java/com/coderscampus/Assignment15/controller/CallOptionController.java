package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.CallOptionRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.CallOptionRecommendationRepository;
import com.coderscampus.Assignment15.repository.CommentRepository;

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
@RequestMapping("/calls")
public class CallOptionController {

    @Autowired
    private CallOptionRecommendationRepository callRepo;
    
    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String listCalls(Model model) {
        List<CallOptionRecommendation> calls = callRepo.findAllByOrderBySubmittedAtDesc();
        model.addAttribute("calls", calls);
        return "callList"; // or whatever your template is named
    }
    
    @GetMapping("/{id}")
    public String viewCall(@PathVariable Long id, Model model) {
        CallOptionRecommendation call = callRepo.findById(id).orElseThrow();
        List<Comment> comments = commentRepo.findByCallOptionIdOrderByCreatedAtDesc(id);
        model.addAttribute("call", call);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        return "callDetail";
    }
    
    @PostMapping("/{id}/comments")
    public String submitComment(@PathVariable Long id,
                                @ModelAttribute Comment newComment,
                                @AuthenticationPrincipal Object principal) {
        newComment.setCallOptionId(id);

        // ✅ This ensures it gets set regardless of @CreationTimestamp
        if (newComment.getCreatedAt() == null) {
            newComment.setCreatedAt(LocalDateTime.now());
        }

        if (principal instanceof OAuth2User oauthUser) {
            newComment.setAuthor(oauthUser.getAttribute("email"));
        } else if (principal instanceof UserDetails userDetails) {
            newComment.setAuthor(userDetails.getUsername());
        }

        commentRepo.save(newComment);
        return "redirect:/calls/" + id;
    }


    @GetMapping("/new")
    public String showCallForm(Model model) {
        model.addAttribute("callOption", new CallOptionRecommendation());
        return "callForm"; // Use the Thymeleaf form I gave earlier
    }

    @PostMapping("/submit")
    public String submitCall(@ModelAttribute CallOptionRecommendation callOption,
                             @AuthenticationPrincipal Object principal) {
        callOption.setSubmittedAt(LocalDateTime.now());
        callRepo.save(callOption);
        return "redirect:/calls";
    }

}
