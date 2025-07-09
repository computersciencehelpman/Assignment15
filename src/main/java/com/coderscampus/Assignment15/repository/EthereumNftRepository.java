package com.coderscampus.Assignment15.repository;

import com.coderscampus.Assignment15.domain.EthereumNftRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EthereumNftRepository extends JpaRepository<EthereumNftRecommendation, Long> {
    List<EthereumNftRecommendation> findAllByOrderByCreatedAtDesc();
}
