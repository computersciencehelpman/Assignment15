package com.coderscampus.Assignment15.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Analysis")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Investment investment;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false, length = 20)
    private String recommendation; // "Buy", "Hold", "Sell"

    @Column(nullable = false)
    private BigDecimal recommendedPrice;

    @Column(name = "execution_time")
    private LocalDateTime timestamp; // Optional: when the user thinks the timing is best

    @Column(name = "created_at")
    private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Investment getInvestment() {
		return investment;
	}
	public void setInvestment(Investment investment) {
		this.investment = investment;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public BigDecimal getRecommendedPrice() {
		return recommendedPrice;
	}
	public void setRecommendedPrice(BigDecimal recommendedPrice) {
		this.recommendedPrice = recommendedPrice;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Analysis [id=" + id + ", user=" + user + ", investment=" + investment + ", title=" + title + ", body="
				+ body + ", recommendation=" + recommendation + ", recommendedPrice=" + recommendedPrice
				+ ", timestamp=" + timestamp + ", createdAt=" + createdAt + "]";
	}

}
