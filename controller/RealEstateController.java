package com.coderscampus.Assignment15.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class RealEstateController {

	@GetMapping("/residential")
    public String Residential() {
        return "residential"; 
    }
	
	@GetMapping("/commercial")
    public String Commercial() {
        return "commercial"; 
    }
	
	@GetMapping("/reits")
    public String Reits() {
        return "reits"; 
    }
	
	@GetMapping("/land")
    public String Land() {
        return "land"; 
    }
	
}
