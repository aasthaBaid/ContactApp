package com.userregistration;

public class FreeUser extends User {

    // Normal constructor (new registration)
    public FreeUser(String email, String password, String fullName, String phoneNumber) throws ValidationException {
        super(email, password, fullName, phoneNumber);
    }

    // Constructor for loading from file (hashed password)
    public FreeUser(String email, String hashedPassword, String fullName, String phoneNumber, boolean isHashed) {
        super(email, hashedPassword, fullName, phoneNumber, true);
    }

    @Override
    public String getUserType() {
        return "Free User";
    }
}