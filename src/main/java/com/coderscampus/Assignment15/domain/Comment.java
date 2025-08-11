package com.coderscampus.Assignment15.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


    @Column(columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String author;

    @ManyToOne
    private StockRecommendation recommendation;

    @Column(name = "call_option_id")
    private Long callOptionId;

    @Column(name = "put_option_id")
    private Long putOptionId;

    @Column(name = "bitcoin_recommendation_id")
    private Long bitcoinRecommendationId;

    @Column(name = "ethereum_recommendation_id")
    private Long ethereumRecommendationId;

    @Column(name = "solana_recommendation_id")
    private Long solanaRecommendationId;

    @Column(name = "cardano_recommendation_id")
    private Long cardanoRecommendationId;

    @Column(name = "polygon_recommendation_id")
    private Long polygonRecommendationId;

    @Column(name = "other_blockchains_recommendation_id")
    private Long otherBlockchainsRecommendationId;

    @Column(name = "bitcoin_nft_recommendation_id")
    private Long bitcoinNftRecommendationId;

    @Column(name = "ethereum_nft_recommendation_id")
    private Long ethereumNftRecommendationId;

    @Column(name = "solana_nft_recommendation_id")
    private Long solanaNftRecommendationId;

    @Column(name = "cardano_nft_recommendation_id")
    private Long cardanoNftRecommendationId;

    @Column(name = "polygon_nft_recommendation_id")
    private Long polygonNftRecommendationId;

    @Column(name = "other_nft_blockchains_recommendation_id")
    private Long otherNftBlockchainsRecommendationId;

    @Column(name = "residential_recommendation_id")
    private Long residentialRecommendationId;

    @Column(name = "commercial_recommendation_id")
    private Long commercialRecommendationId;

    @Column(name = "reit_recommendation_id")
    private Long reitRecommendationId;

    @Column(name = "land_recommendation_id")
    private Long landRecommendationId;

    @Column(name = "collectable_recommendation_id")
    private Long collectableRecommendationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public StockRecommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(StockRecommendation recommendation) {
		this.recommendation = recommendation;
	}

	public Long getCallOptionId() {
		return callOptionId;
	}

	public void setCallOptionId(Long callOptionId) {
		this.callOptionId = callOptionId;
	}

	public Long getPutOptionId() {
		return putOptionId;
	}

	public void setPutOptionId(Long putOptionId) {
		this.putOptionId = putOptionId;
	}

	public Long getBitcoinRecommendationId() {
		return bitcoinRecommendationId;
	}

	public void setBitcoinRecommendationId(Long bitcoinRecommendationId) {
		this.bitcoinRecommendationId = bitcoinRecommendationId;
	}

	public Long getEthereumRecommendationId() {
		return ethereumRecommendationId;
	}

	public void setEthereumRecommendationId(Long ethereumRecommendationId) {
		this.ethereumRecommendationId = ethereumRecommendationId;
	}

	public Long getSolanaRecommendationId() {
		return solanaRecommendationId;
	}

	public void setSolanaRecommendationId(Long solanaRecommendationId) {
		this.solanaRecommendationId = solanaRecommendationId;
	}

	public Long getCardanoRecommendationId() {
		return cardanoRecommendationId;
	}

	public void setCardanoRecommendationId(Long cardanoRecommendationId) {
		this.cardanoRecommendationId = cardanoRecommendationId;
	}

	public Long getPolygonRecommendationId() {
		return polygonRecommendationId;
	}

	public void setPolygonRecommendationId(Long polygonRecommendationId) {
		this.polygonRecommendationId = polygonRecommendationId;
	}

	public Long getOtherBlockchainsRecommendationId() {
		return otherBlockchainsRecommendationId;
	}

	public void setOtherBlockchainsRecommendationId(Long otherBlockchainsRecommendationId) {
		this.otherBlockchainsRecommendationId = otherBlockchainsRecommendationId;
	}

	public Long getBitcoinNftRecommendationId() {
		return bitcoinNftRecommendationId;
	}

	public void setBitcoinNftRecommendationId(Long bitcoinNftRecommendationId) {
		this.bitcoinNftRecommendationId = bitcoinNftRecommendationId;
	}

	public Long getEthereumNftRecommendationId() {
		return ethereumNftRecommendationId;
	}

	public void setEthereumNftRecommendationId(Long ethereumNftRecommendationId) {
		this.ethereumNftRecommendationId = ethereumNftRecommendationId;
	}

	public Long getSolanaNftRecommendationId() {
		return solanaNftRecommendationId;
	}

	public void setSolanaNftRecommendationId(Long solanaNftRecommendationId) {
		this.solanaNftRecommendationId = solanaNftRecommendationId;
	}

	public Long getCardanoNftRecommendationId() {
		return cardanoNftRecommendationId;
	}

	public void setCardanoNftRecommendationId(Long cardanoNftRecommendationId) {
		this.cardanoNftRecommendationId = cardanoNftRecommendationId;
	}

	public Long getPolygonNftRecommendationId() {
		return polygonNftRecommendationId;
	}

	public void setPolygonNftRecommendationId(Long polygonNftRecommendationId) {
		this.polygonNftRecommendationId = polygonNftRecommendationId;
	}

	public Long getOtherNftBlockchainsRecommendationId() {
		return otherNftBlockchainsRecommendationId;
	}

	public void setOtherNftBlockchainsRecommendationId(Long otherNftBlockchainsRecommendationId) {
		this.otherNftBlockchainsRecommendationId = otherNftBlockchainsRecommendationId;
	}

	public Long getResidentialRecommendationId() {
		return residentialRecommendationId;
	}

	public void setResidentialRecommendationId(Long residentialRecommendationId) {
		this.residentialRecommendationId = residentialRecommendationId;
	}

	public Long getCommercialRecommendationId() {
		return commercialRecommendationId;
	}

	public void setCommercialRecommendationId(Long commercialRecommendationId) {
		this.commercialRecommendationId = commercialRecommendationId;
	}

	public Long getReitRecommendationId() {
		return reitRecommendationId;
	}

	public void setReitRecommendationId(Long reitRecommendationId) {
		this.reitRecommendationId = reitRecommendationId;
	}

	public Long getLandRecommendationId() {
		return landRecommendationId;
	}

	public void setLandRecommendationId(Long landRecommendationId) {
		this.landRecommendationId = landRecommendationId;
	}

	public Long getCollectableRecommendationId() {
		return collectableRecommendationId;
	}

	public void setCollectableRecommendationId(Long collectableRecommendationId) {
		this.collectableRecommendationId = collectableRecommendationId;
	}

}
  