package com.kt.service.order;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.exception.ErrorCode;
import com.kt.common.support.Lock;
import com.kt.common.support.Message;
import com.kt.common.support.PreConditions;
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
	private final ApplicationEventPublisher publisher;

	// 주문 관련 비즈니스 로직 구현
	@Lock(key = Lock.Key.PRODUCT)
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

		// 저수준 모듈이 고수준 모듈을 의존하는 문제 발생
		Message message = new Message("User " + user.getName() + " ordered " + quantity * product.getPrice() + " yuan.");
		publisher.publishEvent(message);
	}
}
