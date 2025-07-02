package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment15.domain.StockRecommendation;

public interface StockRecommendationRepository extends JpaRepository<StockRecommendation, Long> {
	List<StockRecommendation> findAllByOrderByCreatedAtDesc();
}

