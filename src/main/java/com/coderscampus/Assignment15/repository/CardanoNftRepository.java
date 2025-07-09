package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.Assignment15.domain.CardanoNftRecommendation;

public interface CardanoNftRepository extends JpaRepository<CardanoNftRecommendation, Long> {
    List<CardanoNftRecommendation> findAllByOrderByCreatedAtDesc();
}
