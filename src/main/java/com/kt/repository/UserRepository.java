package com.kt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Page<User> findAllByNameContaining(String name, Pageable pageable);

	Boolean existsByLoginId(String loginId);

	default User findByIdOrThrow(Long id){
		return findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
	}
}
