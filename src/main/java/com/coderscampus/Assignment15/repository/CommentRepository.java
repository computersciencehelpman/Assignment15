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
	
}

