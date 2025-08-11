package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coderscampus.Assignment15.domain.CallOptionRecommendation;

public interface CallOptionRecommendationRepository extends JpaRepository<CallOptionRecommendation, Long> {
	List<CallOptionRecommendation> findAllByOrderBySubmittedAtDesc();

}
