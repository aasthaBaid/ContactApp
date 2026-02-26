package com.userregistration;

public class PremiumUser extends User {

	public PremiumUser(String email, String password, String fullName, String phoneNumber) throws ValidationException {
		super(email, password, fullName, phoneNumber);
	}

	@Override
	public String getUserType() {
		return "Premium User";
	}

	
}
