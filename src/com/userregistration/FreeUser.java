package com.userregistration;

public class FreeUser extends User {
	@Override
	public String getUserType() {
		return "Free User";
	}

	public FreeUser(String email, String password, String fullName, String phoneNumber) throws ValidationException{
		super(email, password, fullName, phoneNumber);
	}
	
}
