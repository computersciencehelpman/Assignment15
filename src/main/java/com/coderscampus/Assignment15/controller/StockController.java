package com.coderscampus.Assignment15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.Assignment15.domain.StockRecommendation;

@Controller
public class StockController {

//    @GetMapping("/stocks/new")
//    public String showStockForm(Model model) {
//        model.addAttribute("stockForm", new StockRecommendation());
//        return "stock-form";
//    }
    
    @PostMapping("/stocks/new")
    public String submitRecommendation(@ModelAttribute("stockForm") StockRecommendation recommendation) {
        // Save it to a list or DB (depending on how you're storing things)
        System.out.println("New Recommendation: " + recommendation);
        return "redirect:/stocks";
    }

}

