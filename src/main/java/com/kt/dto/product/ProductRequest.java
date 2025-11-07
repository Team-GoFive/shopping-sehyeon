package com.kt.dto.product;

public class ProductRequest {

	public record Create(
		String name,
		Long price,
		Long quantity
	){

	}

	public record Update(
		String name,
		Long price,
		Long quantity
	){

	}
}
