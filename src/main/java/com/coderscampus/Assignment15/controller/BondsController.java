package com.coderscampus.Assignment15.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class BondsController {

	@GetMapping("/internationalbonds")
    public String InternationalBonds() {
        return "internationalbonds"; 
    }
	
	@GetMapping("/usbonds")
    public String USBonds() {
        return "usbonds"; 
    }
	
	@GetMapping("/corporatebonds")
    public String CorporateBonds() {
        return "corporatebonds"; 
    }
	
}
