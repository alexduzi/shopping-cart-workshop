package com.alexduzi.shoppingcart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexduzi.shoppingcart.exceptions.AlreadyExistsException;
import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Category;
import com.alexduzi.shoppingcart.response.ApiResponse;
import com.alexduzi.shoppingcart.service.ICategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/categories")
@AllArgsConstructor
public class CategoryController {

	private final ICategoryService categoryService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories() {
		try {
			List<Category> categories = categoryService.getAllCategories();

			return ResponseEntity.ok(new ApiResponse("Found!", categories));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error: ", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
		try {
			Category newCategory = categoryService.addCategory(category);

			return ResponseEntity.ok(new ApiResponse("Success", newCategory));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/category/{id}/category")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
		try {
			Category category = categoryService.getCategoryById(id);

			return ResponseEntity.ok(new ApiResponse("Found", category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), HttpStatus.NOT_FOUND));
		}
	}

	@GetMapping("/category/{name}/category")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
		try {
			Category category = categoryService.getCategoryByName(name);

			return ResponseEntity.ok(new ApiResponse("Found", category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), HttpStatus.NOT_FOUND));
		}
	}

	@DeleteMapping("/category/{id}/delete")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
		try {
			categoryService.deleteCategoryById(id);

			return ResponseEntity.ok(new ApiResponse("Found", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), HttpStatus.NOT_FOUND));
		}
	}
	
	@PutMapping("/category/{id}/update")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
		try {
			Category updatedCategory = categoryService.updateCategory(category, id);

			return ResponseEntity.ok(new ApiResponse("Found", updatedCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), HttpStatus.NOT_FOUND));
		}
	}
}
