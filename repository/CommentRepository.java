package com.coderscampus.Assignment15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment15.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByCallOptionIdOrderByCreatedAtDesc(Long id);
	List<Comment> findByPutOptionIdOrderByCreatedAtDesc(Long id);
	List<Comment> findByRecommendationIdOrderByCreatedAtDesc(Long id);
	List<Comment> findByBitcoinRecommendationIdOrderByCreatedAtDesc(Long id);
	List<Comment> findByEthereumRecommendationIdOrderByCreatedAtDesc(Long id);
	List<Comment> findBySolanaRecommendationIdOrderByCreatedAtDesc(Long id);
	List<Comment> findByCardanoRecommendationIdOrderByCreatedAtDesc(Long id);
	List<Comment> findByPolygonRecommendationIdOrderByCreatedAtDesc(Long id);
	List<Comment> findByOtherBlockchainsRecommendationIdOrderByCreatedAtDesc(Long id);

}

