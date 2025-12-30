package com.kt.service.faq;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.faq.FAQ;
import com.kt.dto.faq.FAQRequest;
import com.kt.repository.faq.FAQRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FAQService {
	private final FAQRepository fAQRepository;

	public void create(FAQRequest.Create request) {
		fAQRepository.save(
			new FAQ(
				request.title(),
				request.content(),
				request.category()
			)
		);
	}
}
