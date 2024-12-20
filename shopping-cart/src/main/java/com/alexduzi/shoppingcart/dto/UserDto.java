package com.alexduzi.shoppingcart.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private List<OrderDto> orders = new ArrayList<>();
	private CartDto cart;
}
