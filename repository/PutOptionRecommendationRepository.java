package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.Assignment15.domain.PutOptionRecommendation;

public interface PutOptionRecommendationRepository extends JpaRepository<PutOptionRecommendation, Long> {
	List<PutOptionRecommendation> findAllByOrderBySubmittedAtDesc();

}
