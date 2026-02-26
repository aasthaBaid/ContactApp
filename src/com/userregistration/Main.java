package com.userregistration;

public class Main {
	public static void main(String[] args) {
		try {

			// Registration
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

			System.out.println("User1 Type: " + user1.getUserType());
			System.out.println("User2 Type: " + user2.getUserType());

			// Profile Update
			user1.setFullName("John Smith");
			System.out.println("Updated Name: " + user1.getFullName());

			//printing all fields.
			System.out.println(user1.toString());
			System.out.println(user2.toString());


		} catch (ValidationException e) {

			System.out.println("Validation Error: " + e.getMessage());

		} catch (Exception e) {

			System.out.println("Unexpected Error: " + e.getMessage());
		}
	}
}

