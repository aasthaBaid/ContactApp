package com.userauthentication;

import java.util.Optional;
import com.userregistration.User;
import com.userregistration.ValidationException;

public interface Authentication {

	Optional<User> login(String email, String password) throws ValidationException;
	void logout(User user);
}
