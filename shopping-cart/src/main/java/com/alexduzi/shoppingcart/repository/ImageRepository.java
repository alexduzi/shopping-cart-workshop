package com.alexduzi.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexduzi.shoppingcart.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
