package com.kt.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.domain.product.Product;
import com.kt.dto.ProductRegisterRequest;
import com.kt.dto.ProductUpdateRequest;
import com.kt.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService service;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<Product> search(
		@RequestParam int page,
		@RequestParam int size
	) {
		return service.search(PageRequest.of(page - 1, size));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Product detail(@PathVariable Long id) {
		return service.detail(id);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody ProductRegisterRequest request) {
		service.create(
			request.name(),
			request.price(),
			request.stock()
		);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
		service.update(
			id,
			request.name(),
			request.price(),
			request.stock()
		);
	}

	@PutMapping("/{id}/sold-out")
	@ResponseStatus(HttpStatus.OK)
	public void soldOut(@PathVariable Long id) {
		service.soldOut(id);
	}

	@PutMapping("/{id}/activate")
	@ResponseStatus(HttpStatus.OK)
	public void activate(@PathVariable Long id) {
		service.activate(id);
	}

	@PutMapping("/{id}/in-activate")
	@ResponseStatus(HttpStatus.OK)
	public void inActivate(@PathVariable Long id){
		service.inActive(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
