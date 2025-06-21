package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.dto.LoginDto;
import com.coderscampus.Assignment15.domain.User;
import com.coderscampus.Assignment15.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/doLogin")
    public String loginUser(
        @Valid @ModelAttribute("loginDto") LoginDto loginDto,
        BindingResult result,
        Model model,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Optional<User> userOpt = userRepository.findByEmail(loginDto.getEmail());
        if (userOpt.isEmpty()) {
            result.rejectValue("email", null, "No account found with this email.");
            return "login";
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            result.rejectValue("password", null, "Incorrect password.");
            return "login";
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        securityContextRepository.saveContext(
            SecurityContextHolder.getContext(),
            request,
            response
        );

        return "redirect:/profile";
    }

}
