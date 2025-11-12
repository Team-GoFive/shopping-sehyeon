package com.kt.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.ErrorCode;
import com.kt.common.PreConditions;
import com.kt.domain.order.Order;
import com.kt.domain.order.Receiver;
import com.kt.domain.orderprodut.OrderProduct;
import com.kt.repository.ProductRepository;
import com.kt.repository.UserRepository;
import com.kt.repository.order.OrderRepository;
import com.kt.repository.orderproduct.OrderProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;

	// 주문 관련 비즈니스 로직 구현
	public void create(
		Long userId,
		Long productId,
		Long quantity,
		String receiverName,
		String receiverAddress,
		String receiverMobile
	) {
		var product = productRepository.findByIdOrThrow(productId);

		PreConditions.validate(product.canProvide(quantity), ErrorCode.NOT_ENOUGH_STOCK);

		var user = userRepository.findByIdOrThrow(userId);

		var receiver = new Receiver(
			receiverName,
			receiverAddress,
			receiverMobile
		);

		var order = Order.create(receiver, user);
		orderRepository.save(order);
		var orderProduct = new OrderProduct(order, product, quantity);
		orderProductRepository.save(orderProduct);

		product.decreaseStock(quantity);
		product.mapToOrderProduct(orderProduct);
		order.mapToOrderProduct(orderProduct);
	}
}
