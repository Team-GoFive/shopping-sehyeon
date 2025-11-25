package com.kt.domain.history;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kt.common.support.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class History extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private HistoryType type;

	private String content;

	private Long userId;

	public History(HistoryType type, String content, Long userId) {
		this.type = type;
		this.content = content;
		this.userId = userId;
	}
}
