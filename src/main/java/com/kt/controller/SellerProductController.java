package com.kt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dto.ProductRegisterRequest;
import com.kt.dto.ProductStatusUpdateRequest;
import com.kt.dto.ProductUpdateRequest;
import com.kt.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seller/product")
@RequiredArgsConstructor
public class SellerProductController {
	private final ProductService service;

	// 상품 등록
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@RequestBody ProductRegisterRequest request) {
		service.register(request);
	}

	// 상품 삭제
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	// 상품 수정
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
		service.update(id, request);
	}
	// 상품 상태 변경
	@PutMapping("/update/status/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateStatus(@PathVariable Long id, @RequestBody ProductStatusUpdateRequest request) {
		service.updateStatus(id, request);
	}
}
