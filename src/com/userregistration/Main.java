package com.userregistration;

import java.util.Optional;

import com.userauthentication.BasicAuth;
import com.userauthentication.Authentication;

public class Main {

    public static void main(String[] args) {

        try {

            User user1 = new FreeUser(
                    "john@example.com",
                    "password123",
                    "John",
                    "1234567890");

            User user2 = new PremiumUser(
                    "alice@example.com",
                    "secure456",
                    "Alice",
                    "2345678901");

            // Authentication
            Authentication auth = new BasicAuth();
            BasicAuth basicAuth = (BasicAuth) auth;

            // Register users into auth system
            basicAuth.registerUser(user1);
            basicAuth.registerUser(user2);

            // Login attempt
            Optional<User> loggedInUser =
                    auth.login("john@example.com", "password123");

            if (loggedInUser.isPresent()) {
                System.out.println("Login Successful!");
                System.out.println("Welcome " + loggedInUser.get().getFullName());
            } else {
                System.out.println("Invalid Credentials!");
            }

            // Logout
            loggedInUser.ifPresent(user -> auth.logout(user));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}