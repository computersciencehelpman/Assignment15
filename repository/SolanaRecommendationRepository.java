package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coderscampus.Assignment15.domain.SolanaRecommendation;

public interface SolanaRecommendationRepository extends JpaRepository<SolanaRecommendation, Long> {
    List<SolanaRecommendation> findAllByOrderByCreatedAtDesc();
}
