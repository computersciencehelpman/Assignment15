package com.coderscampus.Assignment15.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Investments")
public class Investment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	private String name;        // e.g., "Apple Inc."

	@Column(nullable = false, unique = true, length = 50)
	private String ticker;      // e.g., "AAPL"

	@Column(nullable = false, unique = true, length = 50)
	private String type;        // e.g., "Stock", "Crypto"

	@Column(nullable = false, unique = true, length = 50)
	private String sector;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Investment [id=" + id + ", name=" + name + ", ticker=" + ticker + ", type=" + type + ", sector="
				+ sector + ", createdAt=" + createdAt + "]";
	}

}
