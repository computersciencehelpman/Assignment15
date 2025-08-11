package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.PolygonNftRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolygonNftRepository extends JpaRepository<PolygonNftRecommendation, Long> {
    List<PolygonNftRecommendation> findAllByOrderByCreatedAtDesc();
}