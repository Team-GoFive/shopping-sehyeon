package com.kt.service.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.product.Product;
import com.kt.dto.ProductStatusUpdateRequest;
import com.kt.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
	private final ProductRepository repository;

	public void create(String name, Long price, Long stock){
		var product = Product.create(name, price, stock);
		repository.save(product);
	}

	public void update(Long id, String name, Long price, Long stock){
		var product = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.update(name, price, stock);
	}

	public void soldOut(Long id){
		var product = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.soldOut();
	}

	public void activate(Long id){
		var product = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.active();

	}

	public void inActive(Long id){
		var product = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.inActive();
	}

	public void delete(Long id){
		var product = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.delete();
	}

	public void decreaseStock(Long id, int quantity){
		var product = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.decreaseStock(quantity);
	}

	public void increaseStock(Long id, int quantity){
		var product = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.increaseStock(quantity);
	}

	public Page<Product> search(PageRequest request) {
		return repository.findAll(request);
	}

	public Product detail(Long id) {
		return repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
	}
}
