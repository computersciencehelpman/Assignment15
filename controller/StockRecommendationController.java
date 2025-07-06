package com.coderscampus.Assignment15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.Assignment15.domain.StockRecommendation;
import com.coderscampus.Assignment15.repository.StockRecommendationRepository;

@Controller
public class StockRecommendationController {

    private final StockRecommendationRepository repository;

    public StockRecommendationController(StockRecommendationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/stocks/new")
    public String showStockForm(Model model) {
        model.addAttribute("stockRecommendation", new StockRecommendation());
        return "stockForm";
    }
    
    @GetMapping("/stocks")
    public String listAll(Model model) {
        model.addAttribute("recommendations", repository.findAllByOrderByCreatedAtDesc());
        return "stocks";
    }

    @PostMapping("/stocks")
    public String submitForm(@ModelAttribute StockRecommendation stockRecommendation) {
        StockRecommendation savedRecommendation = repository.save(stockRecommendation);
        return "redirect:/stocks/" + savedRecommendation.getId(); // Auto-redirect
    }
    
//    @GetMapping("/stocks/{id}")
//    public String showRecommendation(@PathVariable Long id, Model model) {
//        StockRecommendation recommendation = repository.findById(id).orElse(null);
//        if (recommendation == null) {
//            return "error/404"; // or handle gracefully
//        }
//        model.addAttribute("recommendation", recommendation);
//        return "stockDetail";
//    }


}
