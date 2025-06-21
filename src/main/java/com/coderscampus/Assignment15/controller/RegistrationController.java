package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.User;
import com.coderscampus.Assignment15.dto.UserRegistrationDto;
import com.coderscampus.Assignment15.repository.UserRepository;
import com.coderscampus.Assignment15.service.RecaptchaResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

@Controller
public class RegistrationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Autowired
    private RequestCache requestCache;

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("userDto") UserRegistrationDto dto,
            BindingResult result,
            @RequestParam(name = "g-recaptcha-response", required = false) String captchaResponse,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (result.hasErrors()) {
            return "register";
        }

        // CAPTCHA Verification
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", recaptchaSecret);
        params.add("response", captchaResponse);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> captchaRequest = new HttpEntity<>(params, headers);

        RecaptchaResponse captchaResult = new RestTemplate()
            .postForObject("https://www.google.com/recaptcha/api/siteverify", captchaRequest, RecaptchaResponse.class);

        if (captchaResult == null || !captchaResult.isSuccess()) {
            model.addAttribute("captchaError", "Captcha verification failed");
            return "register";
        }

        // Save User
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Authenticate
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
        requestCache.removeRequest(request, response);

        System.out.println("==> Authenticated: " + authentication.isAuthenticated());
        System.out.println("==> Redirecting to /profile");

        return "redirect:/profile";
    }
}
