package com.kt.dto;

import com.kt.domain.product.ProductStatus;

public record ProductStatusUpdateRequest(
	ProductStatus status
) {
}
