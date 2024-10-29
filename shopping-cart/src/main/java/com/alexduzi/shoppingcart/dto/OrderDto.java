package com.alexduzi.shoppingcart.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class OrderDto {
	private Long orderId;
	private Long userId;
	private LocalDate orderDate;
	private BigDecimal totalAmount;
	private String orderStatus;
	private Set<OrderItemDto> orderItems = new HashSet<>();
}
