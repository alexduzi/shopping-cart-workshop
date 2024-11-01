package com.alexduzi.shoppingcart.config;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.alexduzi.shoppingcart.model.Role;
import com.alexduzi.shoppingcart.model.User;
import com.alexduzi.shoppingcart.repository.RoleRepository;
import com.alexduzi.shoppingcart.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final RoleRepository roleRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
		createDefaultRoleIfNotExists(defaultRoles);
		createDefaultUserIfNotExists();
		createDefaultAdminIfNotExists();
	}

	private void createDefaultUserIfNotExists() {
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		for (int i = 1; i <= 5; i++) {
			String defaultEmail = "user" + i + "@gmail.com";
			if (!userRepository.existsByEmail(defaultEmail)) {
				User user = new User();
				user.setEmail(defaultEmail);
				user.setFirstName("user " + i);
				user.setLastName("user " + i);
				user.setRoles(Set.of(userRole));
				user.setPassword(passwordEncoder.encode("123456"));
				userRepository.save(user);
			}
		}
	}

	private void createDefaultAdminIfNotExists() {
		Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
		for (int i = 1; i <= 2; i++) {
			String defaultEmail = "admin" + i + "@gmail.com";
			if (!userRepository.existsByEmail(defaultEmail)) {
				User user = new User();
				user.setEmail(defaultEmail);
				user.setFirstName("admin");
				user.setLastName("admin" + i);
				user.setRoles(Set.of(adminRole));
				user.setPassword(passwordEncoder.encode("123456"));
				userRepository.save(user);
			}
		}
	}

	private void createDefaultRoleIfNotExists(Set<String> roles) {
		roles.stream().filter(role -> roleRepository.findByName(role).isEmpty()).map(Role::new)
				.forEach(roleRepository::save);
	}
}
