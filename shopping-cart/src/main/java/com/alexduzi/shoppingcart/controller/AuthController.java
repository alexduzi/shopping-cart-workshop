package com.alexduzi.shoppingcart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexduzi.shoppingcart.request.LoginRequest;
import com.alexduzi.shoppingcart.response.ApiResponse;
import com.alexduzi.shoppingcart.response.JwtResponse;
import com.alexduzi.shoppingcart.security.JwtUtils;
import com.alexduzi.shoppingcart.security.ShopUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {

	private final AuthenticationManager authManager;

	private final JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
		try {
			Authentication authentication = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = jwtUtils.generateTokenForUser(authentication);

			ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();

			JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

			return ResponseEntity.ok(new ApiResponse("Login successful", jwtResponse));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
