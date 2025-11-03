package com.kt.dto;

import java.util.List;

import com.kt.domain.User;

public record CustomPage(
	List<User> users, // 데이터 리스트
	int size, // 페이지 당 데이터 수
	int page, // 현재 페이지
	int pages, // 전체 페이지 수
	long totalElements // 전체 데이터 수
) {
}
