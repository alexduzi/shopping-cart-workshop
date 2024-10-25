package com.alexduzi.shoppingcart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Category;
import com.alexduzi.shoppingcart.model.Product;
import com.alexduzi.shoppingcart.repository.CategoryRepository;
import com.alexduzi.shoppingcart.repository.ProductRepository;
import com.alexduzi.shoppingcart.request.AddProductRequest;
import com.alexduzi.shoppingcart.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	
	private final ProductRepository productRepository;
	
	private final CategoryRepository categoryRepository;

	@Override
	public Product addproduct(AddProductRequest request) {
		
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
									.orElseGet(() -> {
										Category newCategory = new Category(request.getCategory().getName());
										return categoryRepository.save(newCategory);
									});
		
		request.setCategory(category);
		
		return productRepository.save(createProduct(request, category));
	}
	
	private Product createProduct(AddProductRequest request, Category category) {
		return new Product(request.getName(), 
						   request.getBrand(),
						   request.getPrice(),
						   request.getInventory(),
						   request.getDescription(),
						   category);
	}
	
	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		existingProduct.setName(request.getName());
		
		Category category = categoryRepository.findByName(request.getCategory().getName());
		
		existingProduct.setCategory(category);
		
		return existingProduct;
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> { throw new  ResourceNotFoundException("Product not found!"); });
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepository.findById(productId)
								.map(existingProduct -> updateExistingProduct(existingProduct, request))
								.map(productRepository::save)
								.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String name) {
		return productRepository.findByCategoryName(name);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String categoryName, String brand) {
		return productRepository.findByCategoryNameAndBrand(categoryName, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand, name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}
}
