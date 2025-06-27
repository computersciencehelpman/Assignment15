package com.coderscampus.Assignment15.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class CommoditiesController {

	@GetMapping("/preciousmetals")
    public String PreciousMetals() {
        return "preciousmetals"; 
    }
	
	@GetMapping("/energy")
    public String Energy() {
        return "energy"; 
    }
	
	@GetMapping("/agriculturalproducts")
    public String AgriculturalProducts() {
        return "agriculturalproducts"; 
    }
	
	@GetMapping("/industrialMetals")
    public String IndustrialMetals() {
        return "industrialmetals"; 
    }
}
