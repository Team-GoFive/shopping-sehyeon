package com.kt.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kt.domain.product.Product;

@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	void 이름으로_상품_검색() {
		// given
		String productName = "테스트 상품";

		Product product = Product.create(productName, 1000L, 10L);
		productRepository.save(product);

		// when
		Product foundProduct = productRepository.findByNameOrThrow(productName);

		// then
		assertEquals(productName, foundProduct.getName());
	}
}