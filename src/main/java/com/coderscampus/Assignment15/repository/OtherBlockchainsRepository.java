package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.OtherBlockchainsRecommendation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OtherBlockchainsRepository extends JpaRepository<OtherBlockchainsRecommendation, Long> {
    List<OtherBlockchainsRecommendation> findAllByOrderByCreatedAtDesc();
}