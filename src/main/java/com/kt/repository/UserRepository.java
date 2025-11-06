package com.kt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Page<User> findAllByNameContaining(String name, Pageable pageable);

	Boolean existsByLoginId(String loginId);
}
