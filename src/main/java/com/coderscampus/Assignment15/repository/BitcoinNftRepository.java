package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.Assignment15.domain.BitcoinNftRecommendation;

public interface BitcoinNftRepository extends JpaRepository<BitcoinNftRecommendation, Long> {
    List<BitcoinNftRecommendation> findAllByOrderByCreatedAtDesc();
}
