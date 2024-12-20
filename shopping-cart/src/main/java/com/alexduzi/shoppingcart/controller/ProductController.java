package com.alexduzi.shoppingcart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexduzi.shoppingcart.dto.ProductDto;
import com.alexduzi.shoppingcart.exceptions.AlreadyExistsException;
import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Product;
import com.alexduzi.shoppingcart.request.AddProductRequest;
import com.alexduzi.shoppingcart.request.ProductUpdateRequest;
import com.alexduzi.shoppingcart.response.ApiResponse;
import com.alexduzi.shoppingcart.service.IProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/products")
@AllArgsConstructor
public class ProductController {
	private final IProductService productService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		List<ProductDto> dto = productService.convertToDto(products);
		return ResponseEntity.ok(new ApiResponse("success", dto));
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable(required = true) Long productId) {
		try {
			Product product = productService.getProductById(productId);
			ProductDto dto = productService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("success", dto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", null));
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest productRequest) {
		try {
			Product product = productService.addproduct(productRequest);
			ProductDto dto = productService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Add product success", dto));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/product/{productId}/update")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId,
			@RequestBody ProductUpdateRequest prodUpdtRequest) {
		try {
			Product updatedProduct = productService.updateProduct(prodUpdtRequest, productId);
			ProductDto dto = productService.convertToDto(updatedProduct);
			return ResponseEntity.ok(new ApiResponse("Update product success", dto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new ApiResponse("Delete product success", productId));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/product/brand")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,
			@RequestParam String productName) {
		try {
			List<Product> products = productService.getProductsByBrandAndName(brandName, productName);

			if (products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			List<ProductDto> dto = productService.convertToDto(products);
			return ResponseEntity.ok(new ApiResponse("success", dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/category/brand")
	public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String brandName,
			@RequestParam String categoryName) {
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(brandName, categoryName);

			if (products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			List<ProductDto> dto = productService.convertToDto(products);
			return ResponseEntity.ok(new ApiResponse("success", dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/product")
	public ResponseEntity<ApiResponse> getProductsByName(@RequestParam String productName) {
		try {
			List<Product> products = productService.getProductsByName(productName);

			if (products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			List<ProductDto> dto = productService.convertToDto(products);
			return ResponseEntity.ok(new ApiResponse("success", dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/brand")
	public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brandName) {
		try {
			List<Product> products = productService.getProductsByBrand(brandName);

			if (products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			List<ProductDto> dto = productService.convertToDto(products);
			return ResponseEntity.ok(new ApiResponse("success", dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/category")
	public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String categoryName) {
		try {
			List<Product> products = productService.getProductsByCategory(categoryName);

			if (products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			List<ProductDto> dto = productService.convertToDto(products);
			return ResponseEntity.ok(new ApiResponse("success", dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/products/total")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName,
			@RequestParam String productName) {
		try {
			Long totalProducts = productService.countProductsByBrandAndName(brandName, productName);

			return ResponseEntity.ok(new ApiResponse("total_products", totalProducts));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
