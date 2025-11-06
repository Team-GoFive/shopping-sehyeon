package com.kt.domain.product;

public enum ProductStatus {
	ACTIVE("판매중"),
	IN_ACTIVE("판매중지"),
	SOLD_OUT("품절"),
	;
	private final String description;

	ProductStatus(String description) {
		this.description = description;
	}

}
