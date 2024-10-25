package com.alexduzi.shoppingcart.service;

import java.util.List;

import com.alexduzi.shoppingcart.dto.ProductDto;
import com.alexduzi.shoppingcart.model.Product;
import com.alexduzi.shoppingcart.request.AddProductRequest;
import com.alexduzi.shoppingcart.request.ProductUpdateRequest;

public interface IProductService {
	Product addproduct(AddProductRequest product);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProduct(ProductUpdateRequest request, Long productId);
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String categoryName, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String category, String name);
	Long countProductsByBrandAndName(String brand, String name);
	ProductDto convertToDto(Product product);
	List<ProductDto> convertToDto(List<Product> products);
}
