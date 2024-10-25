package com.alexduzi.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexduzi.shoppingcart.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	List<Image> findByProductId(Long productId);
}
