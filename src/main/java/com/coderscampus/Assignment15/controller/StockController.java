package com.coderscampus.Assignment15.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.Assignment15.domain.StockRecommendation;
import com.coderscampus.Assignment15.repository.StockRecommendationRepository;

@Controller
public class StockController {

//    @GetMapping("/stocks/new")
//    public String showStockForm(Model model) {
//        model.addAttribute("stockForm", new StockRecommendation());
//        return "stock-form";
//    }
    
	@Autowired
    private StockRecommendationRepository stockRecommendationRepo;
	
	@PostMapping("/stocks/new")
	public String submitRecommendation(@ModelAttribute("stockRecommendation") StockRecommendation recommendation,
	                                   @AuthenticationPrincipal Object principal) {
	    if (principal instanceof OAuth2User oauthUser) {
	        recommendation.setSubmittedBy(oauthUser.getAttribute("email"));
	    } else if (principal instanceof UserDetails userDetails) {
	        recommendation.setSubmittedBy(userDetails.getUsername());
	    } else {
	        recommendation.setSubmittedBy("Anonymous");
	    }
	    System.out.println("Submitted by: " + recommendation.getSubmittedBy());
	    stockRecommendationRepo.save(recommendation);
	    return "redirect:/stocks";
	}


}

