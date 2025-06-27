package com.coderscampus.Assignment15.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class NftsController {

	@GetMapping("/bitcoinnft")
    public String BitcoinNft() {
        return "bitcoinnft"; 
    }
	
	@GetMapping("/ethereumnft")
    public String EthereumNft() {
        return "ethereumnft"; 
    }
	
	@GetMapping("/solananft")
    public String SolanaNft() {
        return "solananft"; 
    }
	
	@GetMapping("/cardanonft")
    public String CardanoNft() {
        return "cardanonft"; 
    }
	
	@GetMapping("/polygonnft")
    public String PolygonNft() {
        return "polygonnft"; 
    }
	
	@GetMapping("/otherblockchains")
    public String OtherBlockchains() {
        return "otherblockchains"; 
    }
}
