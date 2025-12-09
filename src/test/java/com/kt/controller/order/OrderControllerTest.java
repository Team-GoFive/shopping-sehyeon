package com.kt.controller.order;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.domain.product.Product;
import com.kt.dto.order.OrderRequest;
import com.kt.repository.ProductRepository;
import com.kt.security.DefaultCurrentUser;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerTest {

	@Autowired
	protected ProductRepository productRepository;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@Test
	void 주문_생성_성공() throws Exception {

		Product product = new Product("테스트 상품", 10000L, 100L);
		productRepository.save(product);

		OrderRequest.Create create = new OrderRequest.Create(
			product.getId(),
			2L,
			"홍길동",
			"서울시 강남구",
			"010-1234-5678"
		);

		DefaultCurrentUser defaultCurrentUser = new DefaultCurrentUser(
			1L,
			"tryterry"
		);
		mockMvc.perform(
			post("/api/orders")
				.with(user(defaultCurrentUser))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(create))
		).andDo(print());
	}
	// 컨트롤러 테스트는 요청을 쏴보고 응답을 받아봐야 함
	// MockMvc, TestRestTemplate, RestAssured 등 다양한 방법

}