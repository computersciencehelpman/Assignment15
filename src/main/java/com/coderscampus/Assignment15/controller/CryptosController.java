package com.coderscampus.Assignment15.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class CryptosController {

	@GetMapping("/bitcoin")
    public String Bitcoin() {
        return "bitcoin"; 
    }
	
	@GetMapping("/ethereum")
    public String Ethereum() {
        return "ethereum"; 
    }
	
	@GetMapping("/solana")
    public String Solana() {
        return "solana"; 
    }
	
	@GetMapping("/cardano")
    public String Cardano() {
        return "cardano"; 
    }
	
	@GetMapping("/polygon")
    public String Polygon() {
        return "polygon"; 
    }
	
	@GetMapping("/other-chains")
    public String Otherchains() {
        return "other-chains"; 
    }
	
}
