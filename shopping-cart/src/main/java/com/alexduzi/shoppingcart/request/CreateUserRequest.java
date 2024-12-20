package com.alexduzi.shoppingcart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
