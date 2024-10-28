package com.alexduzi.shoppingcart.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.alexduzi.shoppingcart.model.enums.OrderStatus;

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
	private LocalDate orderDate;
	private BigDecimal totalAmount;
	private OrderStatus orderStatus;
	private UserDto user;
	private Set<OrderItemDto> orderItems = new HashSet<>();
}
