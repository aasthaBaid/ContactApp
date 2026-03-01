package com.search;

import com.userregistration.User;
import com.userregistration.ValidationException;

import java.util.*;

public class ContactFilterService implements FilterService<User> {

    @Override
    public List<User> filter(String userEmail, String filterType, String value) {

        List<User> users;

        try {
            users = User.loadUsersFromFile();
        } catch (ValidationException e) {
            System.out.println("Error loading users: " + e.getMessage());
            return new ArrayList<>();
        }

        List<User> result = new ArrayList<>();

        switch (filterType.toLowerCase()) {

            case "type":
                // Filter by user type (Free User / Premium User)
                for (User user : users) {
                    if (user.getUserType().equalsIgnoreCase(value)) {
                        result.add(user);
                    }
                }
                break;

            case "name":
                // Sort by full name (A-Z)
            	users.sort(Comparator.comparing(
            	        user -> user.getFullName().toLowerCase()
            	));                result = users;
                break;

            case "phone":
                // Filter by phone number (partial match allowed)
                for (User user : users) {
                    if (user.getPhoneNumber().contains(value)) {
                        result.add(user);
                    }
                }
                break;

            case "email":
                // Filter by email (partial match)
                for (User user : users) {
                    if (user.getEmail().toLowerCase().contains(value.toLowerCase())) {
                        result.add(user);
                    }
                }
                break;

            default:
                System.out.println("Invalid filter type.");
        }

        return result;
    }
}