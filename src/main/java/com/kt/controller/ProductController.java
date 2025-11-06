package com.kt.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.domain.product.Product;
import com.kt.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService service;

	// 상품 조회(리스트, 페이징)
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<Product> search(
		@RequestParam int page,
		@RequestParam int size
	) {
		return service.searchAll(PageRequest.of(page - 1, size));
	}

	// 상품 조회 단건
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Product detail(@PathVariable Long id) {
		return service.detail(id);
	}
	// todo: 재고 수량 감소
	// todo: 재고 수량 증가
}
