package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.dto.LoginDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public LoginController(AuthenticationManager authenticationManager,
                           SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/doLogin")
    public String loginUser(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        if (result.hasErrors()) {
            return "login";
        }

        try {
            // We treat "username" as email in CustomUserDetailsService
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            securityContextRepository.saveContext(
                SecurityContextHolder.getContext(), request, response
            );

            return "redirect:/profile";
        } catch (Exception ex) {
            result.reject("badCredentials", "Invalid email or password.");
            return "login";
        }
    }
}
