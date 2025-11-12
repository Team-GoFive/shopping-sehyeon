package com.kt.repository.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.kt.domain.order.QOrder;
import com.kt.domain.orderprodut.QOrderProduct;
import com.kt.domain.product.QProduct;
import com.kt.dto.order.OrderResponse;
import com.kt.dto.order.QOrderResponse_Search;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryDslRepositoryImpl implements OrderQueryDslRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final QOrder order = QOrder.order;
	private final QProduct product = QProduct.product;
	private final QOrderProduct orderProduct = QOrderProduct.orderProduct;

	@Override
	public Page<OrderResponse.Search> search(
		String keyword,
		Pageable pageable
	) {

		var booleanBuilder = new BooleanBuilder();

		booleanBuilder.and(containsProductName(keyword));

		var content = jpaQueryFactory
			.select(new QOrderResponse_Search(
				order.id,
				order.receiver.name,
				order.receiver.address,
				orderProduct.quantity,
				product.price.multiply(orderProduct.quantity), // 임시로 1 곱함
				order.status,
				order.createdAt
			))
			.from(order)
			.join(orderProduct).on(orderProduct.order.id.eq(order.id))
			.join(product).on(orderProduct.product.id.eq(product.id))
			.where(booleanBuilder)
			.orderBy(order.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		var total = jpaQueryFactory
			.select(order.id)
			.from(order)
			.join(orderProduct).on(orderProduct.order.id.eq(order.id))
			.join(product).on(orderProduct.product.id.eq(product.id))
			.where(booleanBuilder)
			.fetch().size();

		return new PageImpl<>(content, pageable, total);
	}

	// 상품명으로 검색하는 조건
	// '%keyword%' - 포함
	// 'keyword%' - 앞에 일치
	// '%keyword' - 뒤에 일치
	// keyword가 null이거나 빈값이면 조건을 추가하지 않음
	private BooleanExpression containsProductName(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return null;
		}
		return product.name.containsIgnoreCase(keyword);
	}
}
