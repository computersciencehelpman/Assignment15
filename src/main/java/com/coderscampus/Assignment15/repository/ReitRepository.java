package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.ReitRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReitRepository extends JpaRepository<ReitRecommendation, Long> {
    List<ReitRecommendation> findAllByOrderByCreatedAtDesc();
}
