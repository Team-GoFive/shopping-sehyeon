package com.kt.domain.visitstat;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kt.common.support.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Getter;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class VisitStat extends BaseEntity {
	private String ip;
	private String userAgent;
	private Long userId;
	private LocalDateTime visitedAt;

	public VisitStat(String ip, String userAgent, Long userId) {
		this.ip = ip;
		this.userAgent = userAgent;
		this.userId = userId;
		visitedAt = LocalDateTime.now();
	}
}
