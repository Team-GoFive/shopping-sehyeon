package com.kt.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.kt.common.exception.CustomException;

class ProductTest {

	@Test
	void 객체_생성_실패_이름_NULL() {
		assertThrowsExactly(
			CustomException.class, () -> {
				Product.create(null, 1000L, 10L);
			}
		);
	}

	@Test
	void 객체_생성_실패_가격_NULL() {
		assertThrowsExactly(
			CustomException.class, () -> {
				Product.create("상품명", null, 10L);
			}
		);
	}

	@Test
	void 객체_생성_실패_가격_음수() {
		assertThrowsExactly(
			CustomException.class, () -> {
				Product.create("상품명", -1L, 10L);
			}
		);
	}

	@Test
	void 객체_생성_실패_재고_NULL() {
		assertThrowsExactly(
			CustomException.class, () -> {
				Product.create("상품명", 1000L, null);
			}
		);
	}

	@Test
	void 객체_생성_실패_재고_음수() {
		assertThrowsExactly(
			CustomException.class, () -> {
				Product.create("상품명", 1000L, -1L);
			}
		);
	}

	@Test
	void 객체_생성_성공() {
		Product product = new Product("상품명", 1000L, 10L);

		assertEquals("상품명", product.getName());
		assertEquals(1000L, product.getPrice());
		assertEquals(10L, product.getStock());
	}

}