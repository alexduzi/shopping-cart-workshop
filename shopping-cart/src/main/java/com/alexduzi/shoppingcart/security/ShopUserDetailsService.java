package com.alexduzi.shoppingcart.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alexduzi.shoppingcart.model.User;
import com.alexduzi.shoppingcart.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = Optional.ofNullable(userRepository.findByEmail(username))
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));

		return ShopUserDetails.buildUserDetails(user);
	}
}
