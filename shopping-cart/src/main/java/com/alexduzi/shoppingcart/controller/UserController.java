package com.alexduzi.shoppingcart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexduzi.shoppingcart.dto.UserDto;
import com.alexduzi.shoppingcart.exceptions.AlreadyExistsException;
import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.User;
import com.alexduzi.shoppingcart.request.CreateUserRequest;
import com.alexduzi.shoppingcart.request.UserUpdateRequest;
import com.alexduzi.shoppingcart.response.ApiResponse;
import com.alexduzi.shoppingcart.service.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

	private final IUserService userService;

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
		try {
			User user = userService.getUserById(userId);

			UserDto userDto = userService.convertToDto(user);

			return ResponseEntity.ok(new ApiResponse("success", userDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PostMapping("/user")
	public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest reqUser) {
		try {
			User user = userService.createUser(reqUser);

			UserDto userDto = userService.convertToDto(user);

			return ResponseEntity.ok(new ApiResponse("success", userDto));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest reqUser, @PathVariable Long userId) {
		try {
			User user = userService.updateUser(reqUser, userId);

			UserDto userDto = userService.convertToDto(user);

			return ResponseEntity.ok(new ApiResponse("update success", userDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		try {
			userService.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("delete success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
