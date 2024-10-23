package com.alexduzi.shoppingcart.request;

import java.math.BigDecimal;

import com.alexduzi.shoppingcart.model.Category;

import lombok.Data;

@Data
public class ProductUpdateRequest {
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private Integer inventory;
	private String description;
	private Category category;
}
