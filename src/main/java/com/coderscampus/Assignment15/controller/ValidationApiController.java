package com.coderscampus.Assignment15.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.Assignment15.domain.User;
import com.coderscampus.Assignment15.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class ValidationApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check-username")
    public boolean checkUsername(@RequestParam String username) {
        boolean exists = userRepository.existsByUsername(username);
        System.out.println("Checking username: " + username + " → Exists: " + exists);
        return exists;
    }

    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam String email) {
        boolean exists = userRepository.existsByEmail(email);
        System.out.println("Checking email: " + email + " → Exists: " + exists);
        return exists;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
