package com.coderscampus.Assignment15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToForumHome() {
        return "forum-home"; 
    }    
    
    @GetMapping("/real-estate")
    public String RealEstate() {
        return "real-estate"; 
    }
    
    
    @GetMapping("/nfts")
    public String Nfts() {
        return "nfts"; 
    }
    
    @GetMapping("/cryptos")
    public String Cryptos() {
        return "cryptos"; 
    }
    
    @GetMapping("/stocks-overview")
    public String Stocks() {
        return "stocks"; 
    }
    
    @GetMapping("/options")
    public String Options() {
        return "options"; 
    }
}
