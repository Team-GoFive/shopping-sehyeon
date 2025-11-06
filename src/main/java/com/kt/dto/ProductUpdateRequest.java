package com.kt.dto;

public record ProductUpdateRequest(
	String name,
	Long price,
	Long stock
) {
}
