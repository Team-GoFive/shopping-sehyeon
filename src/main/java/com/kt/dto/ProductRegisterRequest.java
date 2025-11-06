package com.kt.dto;

public record ProductRegisterRequest(
	String name,
	Long price,
	Long stock
) {
}
