package com.kt.dto.faq;

import com.kt.domain.faq.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface FAQRequest {
	@Schema(name = "FAQRequest.Create")
	record Create(
		@NotBlank
		String title,
		@NotBlank
		String content,
		@NotNull
		Category category
	) {
	}
}
