package com.kt.controller.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.aspect.logger.ShoppingLogger;
import com.kt.common.response.ApiResult;
import com.kt.domain.history.HistoryType;
import com.kt.dto.order.OrderRequest;
import com.kt.security.CurrentUser;
import com.kt.service.order.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	// 주문생성
	@PostMapping
	@ShoppingLogger(type = HistoryType.ORDER_CREATE, content = "주문을 생성했습니다.")
	public ApiResult<Void> create(
		@AuthenticationPrincipal CurrentUser currentUser,
		@RequestBody @Valid OrderRequest.Create request
	) {
		// 주문 생성 로직 구현
		orderService.create(
			currentUser.getId(),
			request.productId(),
			request.quantity(),
			request.receiverName(),
			request.receiverAddress(),
			request.receiverMobile()
		);
		return ApiResult.ok();
	}
}
