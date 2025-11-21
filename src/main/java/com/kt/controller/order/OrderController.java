package com.kt.controller.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.dto.order.OrderRequest;
import com.kt.security.DefaultCurrentUser;
import com.kt.service.order.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ApiResult<Void> create(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser,
		@RequestBody @Valid OrderRequest.Create request
	) {
		// 주문 생성 로직 구현
		orderService.create(
			defaultCurrentUser.getId(),
			request.productId(),
			request.quantity(),
			request.receiverName(),
			request.receiverAddress(),
			request.receiverMobile()
		);
		return ApiResult.ok();
	}
}
