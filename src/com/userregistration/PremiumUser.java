package com.userregistration;

public class PremiumUser extends User {

    // Normal constructor
    public PremiumUser(String email, String password, String fullName, String phoneNumber) throws ValidationException {
        super(email, password, fullName, phoneNumber);
    }

    // Constructor for loading from file
    public PremiumUser(String email, String hashedPassword, String fullName, String phoneNumber, boolean isHashed) {
        super(email, hashedPassword, fullName, phoneNumber, true);
    }

    @Override
    public String getUserType() {
        return "Premium User";
    }
}