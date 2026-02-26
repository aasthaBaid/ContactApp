package com.userauthentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.userregistration.User;

public class BasicAuth implements Authentication {
	private List<User> registeredUsers = new ArrayList<>();

    // register user to system (simulate DB save)
    public void registerUser(User user) {
        registeredUsers.add(user);
    }

    @Override
    public Optional<User> login(String email, String password) {

        for (User user : registeredUsers) {
            if (user.getEmail().equals(email) 
                    && user.checkPassword(password)) {

                SessionManager.getInstance().createSession(user);
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    @Override
    public void logout(User user) {
        SessionManager.getInstance().destroySession(user);
    }
}
