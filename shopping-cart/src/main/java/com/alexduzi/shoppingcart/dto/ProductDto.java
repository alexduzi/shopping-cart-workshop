package com.alexduzi.shoppingcart.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private Integer inventory;
	private String description;
	private CategoryDto category;
	private List<ImageDto> images;
}
