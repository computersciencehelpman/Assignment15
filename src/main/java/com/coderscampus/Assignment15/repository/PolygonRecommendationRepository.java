package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.PolygonRecommendation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PolygonRecommendationRepository extends JpaRepository<PolygonRecommendation, Long> {
    List<PolygonRecommendation> findAllByOrderByCreatedAtDesc();
}
