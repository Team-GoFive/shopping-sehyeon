package com.kt.controller.order;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.common.Paging;
import com.kt.repository.order.OrderRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
	private final OrderRepository orderRepository;

	@GetMapping
	public ApiResult<?> search(
		@RequestParam(required = false) String keyword,
		@ParameterObject Paging paging
	) {
		return ApiResult.ok(orderRepository.search(keyword, paging.toPageable()));
	}
}
