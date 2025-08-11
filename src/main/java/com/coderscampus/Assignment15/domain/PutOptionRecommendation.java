package com.coderscampus.Assignment15.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PutOptionRecommendation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;
    private String companyName;

    private Double strikePrice;
    private LocalDate expirationDate;
    private Double premium;
    private Integer contractCount;

    private Double currentPrice;
    private Double targetPrice;

    private String recommendation; 
    private String riskLevel; 
    private Integer confidenceLevel; 

    @Column(length = 2000)
    private String reason;

    @Column(length = 1000)
    private String catalysts;

    @Column(length = 1000)
    private String risks;

    @Column(length = 1000)
    private String exitStrategy;

    private String supportingLink;
    private String tags;

    private LocalDateTime submittedAt;
    private String submittedBy;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Double getStrikePrice() {
		return strikePrice;
	}
	public void setStrikePrice(Double strikePrice) {
		this.strikePrice = strikePrice;
	}
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Double getPremium() {
		return premium;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	public Integer getContractCount() {
		return contractCount;
	}
	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
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
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public Integer getConfidenceLevel() {
		return confidenceLevel;
	}
	public void setConfidenceLevel(Integer confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCatalysts() {
		return catalysts;
	}
	public void setCatalysts(String catalysts) {
		this.catalysts = catalysts;
	}
	public String getRisks() {
		return risks;
	}
	public void setRisks(String risks) {
		this.risks = risks;
	}
	public String getExitStrategy() {
		return exitStrategy;
	}
	public void setExitStrategy(String exitStrategy) {
		this.exitStrategy = exitStrategy;
	}
	public String getSupportingLink() {
		return supportingLink;
	}
	public void setSupportingLink(String supportingLink) {
		this.supportingLink = supportingLink;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}
	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	
    
}
