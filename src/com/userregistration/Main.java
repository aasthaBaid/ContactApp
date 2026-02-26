package com.userregistration;

import java.util.Optional;

import com.userauthentication.BasicAuth;
import com.userauthentication.Authentication;
import com.userprofile.UserProfileManager;

public class Main {

    public static void main(String[] args) {

        try {

            // --- Registration ---
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

            // Save users to file (persisted)
            user1.saveToFile();
            user2.saveToFile();

            // --- Authentication ---
            Authentication auth = new BasicAuth();

            // Login user
            Optional<User> loggedInUser = auth.login("john@example.com", "password123");

            if (loggedInUser.isPresent()) {
                User user = loggedInUser.get();
                System.out.println("Logged in as: " + user.getFullName());

                // --- Profile management ---
                UserProfileManager profileManager = new UserProfileManager(user);

                // Update full name
                profileManager.updateFullName("John Smith");

                // Update phone number
                profileManager.updatePhoneNumber("9876543210");

                // Change password
                profileManager.changePassword("password123", "newPass456");

                // Manage preferences
               
                user1.saveToFile();
                user2.saveToFile();

                // --- Logout ---
                
                auth.logout(user);

            } else {
                System.out.println("Invalid credentials!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}