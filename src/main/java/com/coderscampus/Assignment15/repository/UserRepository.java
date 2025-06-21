package com.coderscampus.Assignment15.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.Assignment15.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); 
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


}
