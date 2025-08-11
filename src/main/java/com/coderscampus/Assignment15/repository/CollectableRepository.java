package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.CollectableRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectableRepository extends JpaRepository<CollectableRecommendation, Long> {
    List<CollectableRecommendation> findAllByOrderByCreatedAtDesc();
}
