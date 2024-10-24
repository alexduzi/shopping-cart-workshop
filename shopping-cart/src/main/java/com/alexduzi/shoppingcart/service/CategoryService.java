package com.alexduzi.shoppingcart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alexduzi.shoppingcart.exceptions.AlreadyExistsException;
import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Category;
import com.alexduzi.shoppingcart.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return Optional.ofNullable(categoryRepository.findByName(name)).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category)
				.filter(c -> !categoryRepository.existsByName(c.getName()))
				.map(categoryRepository::save)
				.orElseThrow(() -> new AlreadyExistsException("Category already exists!"));
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		return Optional.ofNullable(getCategoryById(id))
						.map(oldCategory -> {
							oldCategory.setName(category.getName());
							return categoryRepository.save(oldCategory);
						})
						.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	}

	@Override
	public void deleteCategoryById(Long id) {
		categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> { throw new ResourceNotFoundException("Category not found!"); });
	}

}
