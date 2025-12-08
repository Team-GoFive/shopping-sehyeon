package com.kt.common.support;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseChangeStatusEntity extends BaseEntity {
	protected LocalDateTime changeStatusAt = LocalDateTime.now();
}
