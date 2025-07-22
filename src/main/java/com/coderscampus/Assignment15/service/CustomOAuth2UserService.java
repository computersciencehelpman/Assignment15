package com.coderscampus.Assignment15.service;

import com.coderscampus.Assignment15.domain.User;
import com.coderscampus.Assignment15.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // Debug: print attributes
        System.out.println("OAuth2 Attributes: " + oAuth2User.getAttributes());

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user_info"),
                "Email not found in Google account");
        }

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setPasswordHash("");
            return userRepository.save(newUser);
        });

        return new DefaultOAuth2User(
        	    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
        	    oAuth2User.getAttributes(),
        	    "email" // this must exist in attributes
        	);


    }
}
