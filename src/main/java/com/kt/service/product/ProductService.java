package com.kt.service.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.product.Product;
import com.kt.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
	private final ProductRepository repository;

	public void create(String name, Long price, Long quantity){
		var product = Product.create(name, price, quantity);
		repository.save(product);
	}

	public void update(Long id, String name, Long price, Long quantity){
		var product = repository.findByIdOrThrow(id);
		product.update(name, price, quantity);
	}

	public void soldOut(Long id){
		var product = repository.findByIdOrThrow(id);
		product.soldOut();
	}

	public void activate(Long id){
		var product = repository.findByIdOrThrow(id);
		product.active();

	}

	public void inActive(Long id){
		var product = repository.findByIdOrThrow(id);
		product.inActive();
	}

	public void delete(Long id){
		var product = repository.findByIdOrThrow(id);
		product.delete();
	}

	public void decreaseStock(Long id, int quantity){
		var product = repository.findByIdOrThrow(id);
		product.decreaseStock(quantity);
	}

	public void increaseStock(Long id, int quantity){
		var product = repository.findByIdOrThrow(id);
		product.increaseStock(quantity);
	}

	public Page<Product> search(PageRequest request) {
		return repository.findAll(request);
	}

	public Product detail(Long id) {
		return repository.findByIdOrThrow(id);
	}
}
