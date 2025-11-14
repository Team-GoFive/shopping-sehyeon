package com.kt.service.order;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kt.domain.Gender;
import com.kt.domain.order.Order;
import com.kt.domain.product.Product;
import com.kt.domain.user.Role;
import com.kt.domain.user.User;
import com.kt.repository.ProductRepository;
import com.kt.repository.UserRepository;
import com.kt.repository.order.OrderRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void 주문_생성() {

		User user = new User(
			"testuser",
			"password",
			"Test User",
			"010-0000-0000",
			"email",
			Gender.MALE,
			Role.USER,
			LocalDate.now()
		);
		userRepository.save(user);

		long stock = 100L;
		Product product = new Product(
			"Test Product",
			1000L,
			stock
		);
		productRepository.save(product);

		long quantity = 2L;
		orderService.create(
			user.getId(),
			product.getId(),
			quantity,
			"Receiver Name",
			"Receiver Address",
			"010-1111-2222"
		);

		Product foundPost = productRepository.findByIdOrThrow(product.getId());
		Optional<Order> foundOrder = orderRepository.findAll().stream().findFirst();

		assertThat(foundPost.getStock()).isEqualTo(stock - quantity);
		assertThat(foundOrder).isPresent();
	}
}