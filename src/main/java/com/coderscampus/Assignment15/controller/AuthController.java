package com.coderscampus.Assignment15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	 @GetMapping("/oauth-login")
	    public String loginPage() {
	        return "redirect:/oauth2/authorization/google";
	    }
}
