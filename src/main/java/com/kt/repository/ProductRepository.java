package com.kt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	default Product findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
	}

	default Product findByNameOrThrow(String name) {
		return findByName(name).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
	}

	Optional<Product> findByName(String name);
}
