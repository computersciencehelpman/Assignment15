package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.Assignment15.domain.OtherNftRecommendation;

public interface OtherNftRepository extends JpaRepository<OtherNftRecommendation, Long> {
    List<OtherNftRecommendation> findAllByOrderByCreatedAtDesc();
}
