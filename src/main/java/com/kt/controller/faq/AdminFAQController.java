package com.kt.controller.faq;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.response.ApiResult;
import com.kt.dto.faq.FAQRequest;
import com.kt.service.faq.FAQService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/faqs")
@RequiredArgsConstructor
public class AdminFAQController {
	private final FAQService fAQService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<Void> create(@RequestBody @Valid FAQRequest.Create request) {
		fAQService.create(request);
		return ApiResult.ok();
	}
}
