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

    private String author; // email or username

    @ManyToOne
    private StockRecommendation recommendation;
    
    private Long callOptionId;
    
    private Long putOptionId;

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
    
}
