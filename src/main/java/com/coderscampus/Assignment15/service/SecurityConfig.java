package com.coderscampus.Assignment15.service;

import com.coderscampus.Assignment15.repository.UserRepository;
import com.coderscampus.Assignment15.security.CustomAuthorizationRequestResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          UserRepository userRepository,
                          CustomOAuth2UserService customOAuth2UserService) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return customOAuth2UserService;
    }

    @Bean
    public org.springframework.security.web.authentication.AuthenticationFailureHandler oauth2FailureHandler() {
        return (request, response, exception) -> {
            exception.printStackTrace();
            response.sendRedirect("/login?error");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           ClientRegistrationRepository clientRegistrationRepository) throws Exception {

        // 👇 Force Google account chooser
        CustomAuthorizationRequestResolver customResolver =
            new CustomAuthorizationRequestResolver(clientRegistrationRepository);

        http
            .oauth2Login(oauth -> oauth
                .loginPage("/login")
                .authorizationEndpoint(auth -> auth
                    .authorizationRequestResolver(customResolver) // 👈 this was undefined before
                )
                .defaultSuccessUrl("/profile", true)
                .userInfoEndpoint(userInfo ->
                    userInfo.userService(oAuth2UserService())
                )
                .failureHandler(oauth2FailureHandler())
            );

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login", "/register", "/css/**", "/js/**", "/images/**",
                    "/", "/forum-home",
                    "/stocks/**", "/calls/**", "/puts/**",
                    "/bitcoinnft/**", "/solananft/**", "/ethereumnft/**", "/cardanonft/**",
                    "/polygonnft/**", "/othernft/**", "/cryptos/**", "/reits/**", "/collectables/**"
                ).permitAll()

                .requestMatchers(
                    "/stocks/submit", "/calls/submit", "/puts/submit",
                    "/bitcoinnft/submit", "/solananft/submit", "/ethereumnft/submit", "/cardanonft/submit",
                    "/polygonnft/submit", "/othernft/submit", "/cryptos/submit", "/reits/submit", "/collectables/submit",
                    "/stocks/comments", "/calls/comments", "/puts/comments",
                    "/bitcoinnft/comments", "/solananft/comments", "/ethereumnft/comments", "/cardanonft/comments",
                    "/polygonnft/comments", "/othernft/comments", "/cryptos/comments", "/reits/comments", "/collectables/comments"
                ).authenticated()

                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/profile", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
