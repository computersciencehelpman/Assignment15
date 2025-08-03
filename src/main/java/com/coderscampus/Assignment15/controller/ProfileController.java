package com.coderscampus.Assignment15.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

	@GetMapping("/profile")
	public String profilePage(Model model, @AuthenticationPrincipal Object principal) {
	    System.out.println("Principal class: " + (principal != null ? principal.getClass().getName() : "null"));

	    String name = "Guest";
	    String email = "unknown@example.com";

	    try {
	        if (principal instanceof OAuth2User oauthUser) {
	            Object nameAttr = oauthUser.getAttribute("name");
	            Object emailAttr = oauthUser.getAttribute("email");

	            name = nameAttr instanceof String ? (String) nameAttr : "Google User";
	            email = emailAttr instanceof String ? (String) emailAttr : "unknown@example.com";
	        } else if (principal instanceof UserDetails userDetails) {
	            String username = userDetails.getUsername();
	            name = username != null ? username : "User";
	            email = username != null ? username : "unknown@example.com";
	        }
	    } catch (Exception e) {
	        System.err.println("Error while setting user attributes: " + e.getMessage());
	    }

	    model.addAttribute("name", name);
	    model.addAttribute("email", email);
	    return "profile";
	}


}
