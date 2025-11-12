package com.kt.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderRequest {
	public record Create(
		@NotNull
		Long userId,
		@NotNull
		Long productId,
		@NotNull
		Long quantity,
		@NotBlank
		String receiverName,
		@NotBlank
		String receiverAddress,
		@NotBlank
		String receiverMobile
	) {

	}
}
