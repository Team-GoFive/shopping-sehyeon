package com.kt.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
	ACTIVE("판매중"),
	IN_ACTIVE("판매중지"),
	SOLD_OUT("품절"),
	DELETED("삭제")
	;
	private final String description;
}
