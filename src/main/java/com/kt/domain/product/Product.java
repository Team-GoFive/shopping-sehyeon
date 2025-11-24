package com.kt.domain.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.kt.common.support.BaseEntity;
import com.kt.common.exception.ErrorCode;
import com.kt.common.support.PreConditions;
import com.kt.domain.orderprodut.OrderProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "product")
	private List<OrderProduct> orderProducts = new ArrayList<>();

	public Product(String name, Long price, Long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.status = ProductStatus.ACTIVE;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public static Product create(String name, Long price, Long stock) {
		PreConditions.validate(StringUtils.hasText(name), ErrorCode.INVALID_PARAMETER);
		PreConditions.validate(price != null && price >= 0, ErrorCode.INVALID_PARAMETER);
		PreConditions.validate(stock != null && stock >= 0, ErrorCode.INVALID_PARAMETER);
		return new Product(name, price, stock);
	}

	public void update(String name, Long price, Long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public void soldOut() {
		this.status = ProductStatus.SOLD_OUT;
	}

	public void active() {
		this.status = ProductStatus.ACTIVE;
	}

	public void inActive() {
		this.status = ProductStatus.IN_ACTIVE;
	}

	public void delete() {
		this.status = ProductStatus.DELETED;
	}

	public void decreaseStock(Long stock) {
		this.stock -= stock;
	}

	public void increaseStock(Long stock) {
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

	public boolean canProvide(Long quantity) {
		return this.stock >= quantity;
	}

	public void mapToOrderProduct(OrderProduct orderProduct) {
		this.orderProducts.add(orderProduct);
	}
}
