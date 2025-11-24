package com.kt.common.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record Paging(
	int page,
	int size
) {

	public Pageable toPageable() {
		return PageRequest.of(page - 1, size);
	}
}
