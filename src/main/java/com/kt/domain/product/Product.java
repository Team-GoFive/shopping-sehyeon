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

	public static Product create(String name, Long price, Long stock){
		return new Product(name, price, stock);
	}

	public void update(String name, Long price, Long stock){
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public void soldOut() {
		this.status = ProductStatus.SOLD_OUT;
	}

	public void active(){
		this.status = ProductStatus.ACTIVE;
	}

	public void inActive(){
		this.status = ProductStatus.IN_ACTIVE;
	}

	public void delete(){
		this.status = ProductStatus.DELETED;
	}

	public void decreaseStock(int stock) {
		this.stock -= stock;
	}

	public void increaseStock(int stock) {
		this.stock += stock;
	}


	public void changeProductDetail(String name, Long price, Long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.updatedAt = LocalDateTime.now();
	}

	public void changeStatus(ProductStatus status) {
		this.status = status;
	}
}
