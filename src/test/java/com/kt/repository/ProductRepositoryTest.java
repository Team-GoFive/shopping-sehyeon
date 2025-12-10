package com.kt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kt.domain.product.Product;
import com.kt.service.order.OrderService;

@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderService orderService;

	@Test
	void 이름으로_상품_검색() {
		// given
		String productName = "테스트 상품";

		Product product = Product.create(productName, 1000L, 10L);
		productRepository.save(product);

		// when
		// Product foundProduct = productRepository.findByNameOrThrow(productName);

		// then
		// assertEquals(productName, foundProduct.getName());
	}

	// @Test
	// void 동시에_100명_주문() throws InterruptedException {
	// 	var userList = new ArrayList<User>();
	// 	for (int i = 0; i < 100; i++) {
	// 		userList.add(new User(
	// 			"testuser-" + i,
	// 			"password",
	// 			"Test User-" + i,
	// 			"email-" + i,
	// 			"010-0000-000" + i,
	// 			Gender.MALE,
	// 			Role.USER,
	// 			LocalDate.now()
	// 		));
	// 	}
	//
	// 	var users = userRepository.saveAll(userList);
	//
	// 	var product = productRepository.save(
	// 		new Product(
	// 			"테스트 상품명",
	// 			100_000L,
	// 			10L
	// 		)
	// 	);
	//
	// 	productRepository.flush();
	//
	// 	// 동시에 주문해야하니까 쓰레드를 100개
	// 	var executorService = Executors.newFixedThreadPool(100);
	// 	var countDownLatch = new CountDownLatch(100);
	// 	AtomicInteger successCount = new AtomicInteger(0);
	// 	AtomicInteger failureCount = new AtomicInteger(0);
	//
	// 	for (int i = 0; i < 100; i++) {
	// 		int finalI = i;
	// 		executorService.submit(() -> {
	// 			try {
	// 				var targetUser = users.get(finalI);
	// 				orderService.create(
	// 					targetUser.getId(),
	// 					product.getId(),
	// 					1L,
	// 					targetUser.getName(),
	// 					"수신자 주소-" + finalI,
	// 					"010-1111-22" + finalI
	// 				);
	// 				successCount.incrementAndGet();
	// 			} catch (RuntimeException e) {
	// 				e.printStackTrace();
	// 				failureCount.incrementAndGet();
	// 			} finally {
	// 				countDownLatch.countDown();
	// 			}
	// 		});
	// 	}
	//
	// 	countDownLatch.await();
	// 	executorService.shutdown();
	//
	// 	var foundedProduct = productRepository.findByIdOrThrow(product.getId());
	//
	// 	assertThat(successCount.get()).isEqualTo(10);
	// 	assertThat(failureCount.get()).isEqualTo(90);
	// 	assertThat(foundedProduct.getStock()).isEqualTo(0);
	// }
}