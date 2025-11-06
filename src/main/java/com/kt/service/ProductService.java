package com.kt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.product.Product;
import com.kt.dto.ProductRegisterRequest;
import com.kt.dto.ProductStatusUpdateRequest;
import com.kt.dto.ProductUpdateRequest;
import com.kt.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
	private final ProductRepository repository;

	public Page<Product> searchAll(PageRequest request) {
		return repository.findAll(request);
	}

	public Product detail(Long id) {
		return repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
	}

	public void register(ProductRegisterRequest request) {
		var product = new Product(
			request.name(),
			request.price(),
			request.stock()
		);
		repository.save(product);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public void update(Long id, ProductUpdateRequest request) {
		var product = repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.changeProductDetail(request.name(), request.price(), request.stock());
	}

	public void updateStatus(Long id, ProductStatusUpdateRequest request) {
		var product = repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.changeStatus(request.status());
	}
}
