package com.alexduzi.shoppingcart.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
	private Long id;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private ProductDto product;
	@JsonIgnore
	private CartDto cart;
}
