package com.kt.service.order;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kt.domain.order.Order;
import com.kt.domain.product.Product;
import com.kt.domain.user.Gender;
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

	@Test
	@Disabled
	void 동시에_100명_주문() throws Exception {
		var repeatCount = 500;
		var userList = new ArrayList<User>();
		for (int i = 0; i < repeatCount; i++) {
			userList.add(new User(
				"testuser-" + i,
				"password",
				"Test User-" + i,
				"email-" + i,
				"010-0000-000" + i,
				Gender.MALE,
				Role.USER,
				LocalDate.now()
			));
		}

		var users = userRepository.saveAll(userList);

		var product = productRepository.save(
			new Product(
				"테스트 상품명",
				100_000L,
				10L
			)
		);

		productRepository.flush();

		// 동시에 주문해야하니까 쓰레드를 100개
		var executorService = Executors.newFixedThreadPool(100);
		var countDownLatch = new CountDownLatch(repeatCount);
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failureCount = new AtomicInteger(0);

		for (int i = 0; i < repeatCount; i++) {
			int finalI = i;
			executorService.submit(() -> {
				try {
					var targetUser = users.get(finalI);
					orderService.create(
						targetUser.getId(),
						product.getId(),
						1L,
						"수신자 주소-" + finalI,
						"010-1111-22" + finalI,
						targetUser.getName()
					);
					successCount.incrementAndGet();
				} catch (RuntimeException e) {
					e.printStackTrace();
					failureCount.incrementAndGet();
				} finally {
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		executorService.shutdown();

		var foundedProduct = productRepository.findByIdOrThrow(product.getId());

		// 1번쓰레드에서 작업하다가 언락
		// 2번쓰레드에서 작업하다가 언락

		// assertThat(successCount.get()).isEqualTo(10);
		// assertThat(failureCount.get()).isEqualTo(490);
		// assertThat(foundedProduct.getStock()).isEqualTo(0);
	}
}