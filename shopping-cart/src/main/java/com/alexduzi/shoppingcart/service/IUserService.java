package com.alexduzi.shoppingcart.service;

import com.alexduzi.shoppingcart.dto.UserDto;
import com.alexduzi.shoppingcart.model.User;
import com.alexduzi.shoppingcart.request.CreateUserRequest;
import com.alexduzi.shoppingcart.request.UserUpdateRequest;

public interface IUserService {

	User getUserById(Long userId);

	User createUser(CreateUserRequest user);

	User updateUser(UserUpdateRequest user, Long userId);

	void deleteUser(Long userId);

	UserDto convertToDto(User user);

	User getAuthenticatedUser();
}
