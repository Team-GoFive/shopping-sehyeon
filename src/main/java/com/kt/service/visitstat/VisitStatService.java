package com.kt.service.visitstat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.visitstat.VisitStat;
import com.kt.repository.visitstat.VisitStatRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitStatService {
	private final VisitStatRepository visitStatRepository;

	// ip, userAgent => request
	public void create(String ip, String userAgent, Long userId) {
		VisitStat visitStat = new VisitStat(ip, userAgent, userId);
		visitStatRepository.save(visitStat);
	}
}
