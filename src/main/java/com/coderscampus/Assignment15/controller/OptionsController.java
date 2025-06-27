package com.coderscampus.Assignment15.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class OptionsController {

	@GetMapping("/calls")
    public String Calls() {
        return "calls"; 
    }
	
	@GetMapping("/puts")
    public String Puts() {
        return "puts"; 
    }
}
