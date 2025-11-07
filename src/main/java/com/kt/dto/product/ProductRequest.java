package com.kt.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductRequest {

	@Schema(name = "ProductRequest.Create", description = "상품 생성 요청")
	public record Create(
		String name,
		Long price,
		Long quantity
	){}

	@Schema(name = "ProductRequest.Update", description = "상품 수정 요청")
	public record Update(
		String name,
		Long price,
		Long quantity
	){}
}
