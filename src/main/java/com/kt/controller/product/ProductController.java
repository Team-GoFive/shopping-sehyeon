package com.kt.controller.product;

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

import com.kt.common.response.ApiResult;
import com.kt.common.support.SwaggerAssistance;
import com.kt.dto.product.ProductRequest;
import com.kt.dto.product.ProductResponse;
import com.kt.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController extends SwaggerAssistance {
	private final ProductService service;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> search(
		@RequestParam int page,
		@RequestParam int size
	) {
		var products = service.search(PageRequest.of(page - 1, size));
		var data = products.map(product -> new ProductResponse.Search(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getStock()
		));

		return ApiResult.ok(data);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> detail(@PathVariable Long id) {
		var product = service.detail(id);

		return ApiResult.ok(new ProductResponse.Detail(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getStock()
		));
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<?> create(@RequestBody ProductRequest.Create request) {
		service.create(
			request.name(),
			request.price(),
			request.quantity()
		);
		return ApiResult.ok();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> update(@PathVariable Long id, @RequestBody ProductRequest.Update request) {
		service.update(
			id,
			request.name(),
			request.price(),
			request.quantity()
		);
		return ApiResult.ok();
	}

	@PutMapping("/{id}/sold-out")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> soldOut(@PathVariable Long id) {
		service.soldOut(id);
		return ApiResult.ok();
	}

	@PutMapping("/{id}/activate")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> activate(@PathVariable Long id) {
		service.activate(id);
		return ApiResult.ok();
	}

	@PutMapping("/{id}/in-activate")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> inActivate(@PathVariable Long id) {
		service.inActive(id);
		return ApiResult.ok();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ApiResult.ok();
	}
}
