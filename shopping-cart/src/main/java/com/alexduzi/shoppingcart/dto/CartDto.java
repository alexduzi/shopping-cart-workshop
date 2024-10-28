package com.alexduzi.shoppingcart.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
	private Long id;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	private Set<CartItemDto> items = new HashSet<>();
}
