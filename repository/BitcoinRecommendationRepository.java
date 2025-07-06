package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coderscampus.Assignment15.domain.BitcoinRecommendation;

public interface BitcoinRecommendationRepository extends JpaRepository<BitcoinRecommendation, Long> {
    List<BitcoinRecommendation> findAllByOrderByCreatedAtDesc();
}
