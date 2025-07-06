package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coderscampus.Assignment15.domain.CardanoRecommendation;

public interface CardanoRecommendationRepository extends JpaRepository<CardanoRecommendation, Long> {
    List<CardanoRecommendation> findAllByOrderByCreatedAtDesc();
}
