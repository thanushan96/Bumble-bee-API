package net.bumblebee.service;

import net.bumblebee.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.bumblebee.entity.User;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
