package com.kt.domain.product;

import java.time.LocalDateTime;

import com.kt.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
	private String name;
	private Long price;
	private Long stock;
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	public Product(String name, Long price, Long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.status = ProductStatus.ACTIVE;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public void changeProductDetail(String name, Long price, Long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.updatedAt = LocalDateTime.now();
	}

	public void changeStatus(String status) {
		this.status = ProductStatus.valueOf(status);
	}
}
