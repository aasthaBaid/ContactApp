package com.userregistration;

import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public abstract class User {
	// private fields
	private String email;
	private String password;
	private String fullName;
	
	
	@Override
	public String toString() {
		return "User [email=" + email + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + "]";
	}

	private String phoneNumber;

	// getters and setters methods 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// user type - premium or free
	public abstract String getUserType();
	
	// constructor 
	public User(String email, String password, String fullName, String phoneNumber) throws ValidationException {
		validateEmail(email);
		validatePassword(password);
		this.email = email;
		this.password = hashPassword(password);
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
	}

	// validate email using regex 
	private void validateEmail(String email)
			throws ValidationException {

		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

		if (!Pattern.matches(regex, email)) {
			throw new ValidationException("Invalid Email Format");
		}
	}
// validate passworod
	private void validatePassword(String password)
			throws ValidationException {

		if (password.length() < 6) {
			throw new ValidationException("Password must be at least 6 characters");
		}
	}
	// password hashing using message digest 
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
	// checking password.
	public boolean checkPassword(String password) {
		return this.password.equals(hashPassword(password));
	
	}

}
