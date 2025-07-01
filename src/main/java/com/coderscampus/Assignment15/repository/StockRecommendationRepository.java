package com.coderscampus.Assignment15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment15.domain.StockRecommendation;

@Repository
public interface StockRecommendationRepository extends JpaRepository<StockRecommendation, Long> {
}

