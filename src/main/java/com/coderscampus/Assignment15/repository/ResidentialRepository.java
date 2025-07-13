package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.ResidentialRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidentialRepository extends JpaRepository<ResidentialRecommendation, Long> {
    List<ResidentialRecommendation> findAllByOrderByCreatedAtDesc();
}
