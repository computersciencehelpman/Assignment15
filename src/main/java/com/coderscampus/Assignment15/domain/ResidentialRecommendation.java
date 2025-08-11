package com.coderscampus.Assignment15.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ResidentialRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String city;
    private String state;
    private String zip;

    private Double listingPrice;
    private Double targetPrice;
    private String recommendation; 

    @Lob
    private String reason;

    @Lob
    private String risks;

    private String listingLink; 
    private String submittedBy;
    private LocalDateTime createdAt;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Double getListingPrice() {
		return listingPrice;
	}
	public void setListingPrice(Double listingPrice) {
		this.listingPrice = listingPrice;
	}
	public Double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(Double targetPrice) {
		this.targetPrice = targetPrice;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRisks() {
		return risks;
	}
	public void setRisks(String risks) {
		this.risks = risks;
	}
	public String getListingLink() {
		return listingLink;
	}
	public void setListingLink(String listingLink) {
		this.listingLink = listingLink;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
   
}
