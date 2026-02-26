package com.userregistration;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

public abstract class User {

    // ----- Fields -----
    private String email;
    private String password; // hashed
    private String fullName;
    private String phoneNumber;

    private Map<String, String> preferences = new HashMap<>();

    private static final String FILE_NAME = "users.txt";

    // ----- Constructor for new users -----
    public User(String email, String password, String fullName, String phoneNumber) throws ValidationException {
        validateEmail(email);
        validatePassword(password);

        this.email = email;
        this.password = hashPassword(password); // store hashed password
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    // ----- Constructor for loading from file (hashed password) -----
    public User(String email, String hashedPassword, String fullName, String phoneNumber, boolean isHashed) {
        this.email = email;
        this.password = hashedPassword;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    // ----- Abstract method -----
    public abstract String getUserType();

    // ----- Getters & Setters -----
    public String getEmail() { return email; }
    public String getPassword() { return password; } // hashed
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }

    // ----- Validation -----
    private void validateEmail(String email) throws ValidationException {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(regex, email)) {
            throw new ValidationException("Invalid email format");
        }
    }

    protected void validatePassword(String password) throws ValidationException {
        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters");
        }
    }

    // ----- Password hashing -----
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean checkPassword(String password) {
        return this.password.equals(hashPassword(password));
    }

    // ----- Profile Management -----
    public void updateFullName(String newFullName) throws ValidationException {
        if (newFullName == null || newFullName.trim().isEmpty()) {
            throw new ValidationException("Full name cannot be empty");
        }
        this.fullName = newFullName;
        saveToFile();
    }

    public void updatePhoneNumber(String newPhone) throws ValidationException {
        if (!newPhone.matches("\\d{10}")) {
            throw new ValidationException("Phone number must be 10 digits");
        }
        this.phoneNumber = newPhone;
        saveToFile();
    }

    public void changePassword(String oldPassword, String newPassword) throws ValidationException {
        if (!checkPassword(oldPassword)) {
            throw new ValidationException("Old password is incorrect");
        }
        validatePassword(newPassword);
        this.password = hashPassword(newPassword);
        saveToFile();
    }

    // Preferences
    public void setPreference(String key, String value) throws ValidationException {
        preferences.put(key, value);
        saveToFile();
    }

    public String getPreference(String key) {
        return preferences.get(key);
    }

    public Map<String, String> getAllPreferences() {
        return preferences;
    }

    // ----- File Persistence -----
    public void saveToFile() throws ValidationException {
        try {
            // Load all existing users to avoid duplicates
            List<User> users = loadUsersFromFile();

            // Remove this user if already exists (update)
            users.removeIf(u -> u.getEmail().equals(this.email));
            users.add(this);

            // Write all users back to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (User u : users) {
                    String data = u.getEmail() + "," +
                                  u.getPassword() + "," +
                                  u.getFullName() + "," +
                                  u.getPhoneNumber() + "," +
                                  u.getUserType();
                    writer.write(data);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    public static List<User> loadUsersFromFile() throws ValidationException {
        List<User> users = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String email = parts[0];
                String hashedPassword = parts[1];
                String fullName = parts[2];
                String phone = parts[3];
                String userType = parts[4];

                User user;
                if (userType.equals("Premium User")) {
                    user = new PremiumUser(email, hashedPassword, fullName, phone, true);
                } else {
                    user = new FreeUser(email, hashedPassword, fullName, phone, true);
                }

                users.add(user);
            }

        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", fullName=" + fullName +
               ", phoneNumber=" + phoneNumber + ", type=" + getUserType() + "]";
    }
}