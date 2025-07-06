package com.coderscampus.Assignment15.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profilePage(Model model, @AuthenticationPrincipal Object principal) {
        System.out.println("Principal class: " + principal.getClass().getName());
        if (principal instanceof OAuth2User oauthUser) {
            model.addAttribute("name", oauthUser.getAttribute("name"));
            model.addAttribute("email", oauthUser.getAttribute("email"));
        } else if (principal instanceof UserDetails userDetails) {
            model.addAttribute("name", userDetails.getUsername());
            model.addAttribute("email", userDetails.getUsername()); // Can adjust if real name/email is in DB
        } else {
            model.addAttribute("name", "Guest");
            model.addAttribute("email", "unknown@example.com");
        }
        return "profile";
    }
}
