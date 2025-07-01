package com.coderscampus.Assignment15.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StockRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;
    private String companyName;
    private String exchange;
    private String sector;
    private String industry;
    private Double currentPrice;
    private LocalDate dateOfRecommendation;

    private Double peRatio;
    private Double pegRatio;
    private Double pbRatio;
    private Double evEbitda;
    private Double dividendYield;
    private Double marketCap;

    private Double revenueGrowth;
    private Double epsGrowth;
    private Double debtToEquity;
    private Double roe;
    private Double freeCashFlow;

    private Double targetPrice;
    private String timeHorizon;
    private String recommendation;
    
    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String catalysts;
    @Column(columnDefinition = "TEXT")
    private String risks;

    @Column(columnDefinition = "TEXT")
    private String insiderActivity;
    private Double institutionalOwnership;

    @Column(columnDefinition = "TEXT")
    private String retailSentiment;
    @Column(columnDefinition = "TEXT")
    private String technicalIndicators;
    @Column(columnDefinition = "TEXT")
    private String peers;
    @Column(columnDefinition = "TEXT")
    private String sources;
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
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public LocalDate getDateOfRecommendation() {
		return dateOfRecommendation;
	}
	public void setDateOfRecommendation(LocalDate dateOfRecommendation) {
		this.dateOfRecommendation = dateOfRecommendation;
	}
	public Double getPeRatio() {
		return peRatio;
	}
	public void setPeRatio(Double peRatio) {
		this.peRatio = peRatio;
	}
	public Double getPegRatio() {
		return pegRatio;
	}
	public void setPegRatio(Double pegRatio) {
		this.pegRatio = pegRatio;
	}
	public Double getPbRatio() {
		return pbRatio;
	}
	public void setPbRatio(Double pbRatio) {
		this.pbRatio = pbRatio;
	}
	public Double getEvEbitda() {
		return evEbitda;
	}
	public void setEvEbitda(Double evEbitda) {
		this.evEbitda = evEbitda;
	}
	public Double getDividendYield() {
		return dividendYield;
	}
	public void setDividendYield(Double dividendYield) {
		this.dividendYield = dividendYield;
	}
	public Double getMarketCap() {
		return marketCap;
	}
	public void setMarketCap(Double marketCap) {
		this.marketCap = marketCap;
	}
	public Double getRevenueGrowth() {
		return revenueGrowth;
	}
	public void setRevenueGrowth(Double revenueGrowth) {
		this.revenueGrowth = revenueGrowth;
	}
	public Double getEpsGrowth() {
		return epsGrowth;
	}
	public void setEpsGrowth(Double epsGrowth) {
		this.epsGrowth = epsGrowth;
	}
	public Double getDebtToEquity() {
		return debtToEquity;
	}
	public void setDebtToEquity(Double debtToEquity) {
		this.debtToEquity = debtToEquity;
	}
	public Double getRoe() {
		return roe;
	}
	public void setRoe(Double roe) {
		this.roe = roe;
	}
	public Double getFreeCashFlow() {
		return freeCashFlow;
	}
	public void setFreeCashFlow(Double freeCashFlow) {
		this.freeCashFlow = freeCashFlow;
	}
	public Double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(Double targetPrice) {
		this.targetPrice = targetPrice;
	}
	public String getTimeHorizon() {
		return timeHorizon;
	}
	public void setTimeHorizon(String timeHorizon) {
		this.timeHorizon = timeHorizon;
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
	public String getInsiderActivity() {
		return insiderActivity;
	}
	public void setInsiderActivity(String insiderActivity) {
		this.insiderActivity = insiderActivity;
	}
	public Double getInstitutionalOwnership() {
		return institutionalOwnership;
	}
	public void setInstitutionalOwnership(Double institutionalOwnership) {
		this.institutionalOwnership = institutionalOwnership;
	}
	public String getRetailSentiment() {
		return retailSentiment;
	}
	public void setRetailSentiment(String retailSentiment) {
		this.retailSentiment = retailSentiment;
	}
	public String getTechnicalIndicators() {
		return technicalIndicators;
	}
	public void setTechnicalIndicators(String technicalIndicators) {
		this.technicalIndicators = technicalIndicators;
	}
	public String getPeers() {
		return peers;
	}
	public void setPeers(String peers) {
		this.peers = peers;
	}
	public String getSources() {
		return sources;
	}
	public void setSources(String sources) {
		this.sources = sources;
	}
    
}
