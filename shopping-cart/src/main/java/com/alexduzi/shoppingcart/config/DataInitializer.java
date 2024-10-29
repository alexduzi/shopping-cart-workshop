package com.alexduzi.shoppingcart.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.alexduzi.shoppingcart.model.User;
import com.alexduzi.shoppingcart.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final UserRepository userRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		createDefaultUserIfNotExists();
	}

	private void createDefaultUserIfNotExists() {
		for (int i = 0; i <= 5; i++) {
			String defaultEmail = "user" + i + "@gmail.com";
			if (!userRepository.existsByEmail(defaultEmail)) {
				User user = new User();
				user.setEmail(defaultEmail);
				user.setFirstName("user " + i);
				user.setLastName("user " + i);
				user.setPassword("123456");
				userRepository.save(user);
			}
		}
	}
}
