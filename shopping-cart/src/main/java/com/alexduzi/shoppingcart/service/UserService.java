package com.alexduzi.shoppingcart.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alexduzi.shoppingcart.dto.UserDto;
import com.alexduzi.shoppingcart.exceptions.AlreadyExistsException;
import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.User;
import com.alexduzi.shoppingcart.repository.UserRepository;
import com.alexduzi.shoppingcart.request.CreateUserRequest;
import com.alexduzi.shoppingcart.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public User createUser(CreateUserRequest reqUser) {
		return Optional.of(reqUser).filter(u -> !userRepository.existsByEmail(u.getEmail())).map(rUser -> {
			User user = new User();
			user.setFirstName(rUser.getFirstName());
			user.setEmail(rUser.getEmail());
			user.setLastName(rUser.getLastName());
			user.setPassword(passwordEncoder.encode(rUser.getPassword()));
			return userRepository.save(user);
		}).orElseThrow(() -> new AlreadyExistsException(reqUser.getEmail() + " already exists"));
	}

	@Override
	public User updateUser(UserUpdateRequest user, Long userId) {
		return userRepository.findById(userId).map(existingUser -> {
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			return userRepository.save(existingUser);
		}).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
			throw new ResourceNotFoundException("User not found");
		});
	}

	@Override
	public UserDto convertToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepository.findByEmail(email);
	}
}
