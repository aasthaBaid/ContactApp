package com.userauthentication;

import java.util.Optional;

import com.userregistration.User;

public interface Authentication {

	Optional<User> login(String email, String password);
	void logout(User user);
}
