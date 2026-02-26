package com.userprofile;

import com.userregistration.User;
import com.userauthentication.SessionManager;
import com.userregistration.ValidationException;

public class UserProfileManager {

    private User user;

    public UserProfileManager(User user) throws Exception {
        if (!SessionManager.getInstance().isLoggedIn(user.getEmail())) {
            throw new Exception("User must be logged in to manage profile");
        }
        this.user = user;
    }

    // Update full name
    public void updateFullName(String fullName) {
        try {
            user.updateFullName(fullName);
            System.out.println("Full name updated to: " + fullName);
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Update phone number
    public void updatePhoneNumber(String phoneNumber) {
        try {
            user.updatePhoneNumber(phoneNumber);
            System.out.println("Phone number updated to: " + phoneNumber);
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Change password
    public void changePassword(String oldPassword, String newPassword) {
        try {
            user.changePassword(oldPassword, newPassword);
            System.out.println("Password changed successfully.");
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}