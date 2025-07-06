package com.coderscampus.Assignment15.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class CollectablesController {

	@GetMapping("/art")
    public String Art() {
        return "art"; 
    }
	
	@GetMapping("/wine")
    public String Wine() {
        return "wine"; 
    }
	
	@GetMapping("/whiskey")
    public String Whiskey() {
        return "whiskey"; 
    }
	
	@GetMapping("/antiques")
    public String Antiques() {
        return "antiques"; 
    }
	
	@GetMapping("/classiccars")
    public String ClassicCars() {
        return "classiccars"; 
    }
	
	@GetMapping("/luxurywatches")
    public String LuxuryWatches() {
        return "luxurywatches"; 
    }
}
