package com.kt.service.history;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.history.History;
import com.kt.domain.history.HistoryType;
import com.kt.repository.history.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {

	private final HistoryRepository historyRepository;

	public void create(HistoryType type, String content, Long userId) {
		History history = new History(type, content, userId);
		System.out.println("history = " + history);
		historyRepository.save(history);
	}
}
