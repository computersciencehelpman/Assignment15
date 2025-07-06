package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.EthereumRecommendation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EthereumRecommendationRepository extends JpaRepository<EthereumRecommendation, Long> {
    List<EthereumRecommendation> findAllByOrderByCreatedAtDesc();
}
