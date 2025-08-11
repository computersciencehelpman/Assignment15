package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.CommercialRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommercialRepository extends JpaRepository<CommercialRecommendation, Long> {
    List<CommercialRecommendation> findAllByOrderByCreatedAtDesc();
}
