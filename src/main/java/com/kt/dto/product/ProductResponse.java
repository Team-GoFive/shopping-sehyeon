package com.kt.dto.product;

public class ProductResponse {
	public record Search(
		Long id,
		String name,
		Long price,
		Long quantity
	){

	}

	public record Detail(
		Long id,
		String name,
		Long price,
		Long quantity
	){

	}
}
