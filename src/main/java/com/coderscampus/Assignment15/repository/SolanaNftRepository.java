package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.SolanaNftRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolanaNftRepository extends JpaRepository<SolanaNftRecommendation, Long> {
    List<SolanaNftRecommendation> findAllByOrderByCreatedAtDesc();
}

