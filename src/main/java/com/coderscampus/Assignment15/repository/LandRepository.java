package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.LandRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LandRepository extends JpaRepository<LandRecommendation, Long> {
    List<LandRecommendation> findAllByOrderByCreatedAtDesc();
}
