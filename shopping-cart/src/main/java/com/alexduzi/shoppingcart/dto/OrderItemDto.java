package com.alexduzi.shoppingcart.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
	private long productId;
	private String productName;
	private String productBrand;
	private Integer quantity;
	private BigDecimal price;
}
