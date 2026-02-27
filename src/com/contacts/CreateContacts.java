package com.contacts;


import java.io.*;
import java.io.FileReader;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateContacts {

	// Unique ID for each contact
	private String contactId;
	private String name;
	private String phone;
	private String email;

	// Time when contact was created
	private LocalDateTime createdAt;

	public CreateContacts(String name, String phone, String email) {

		this.contactId = UUID.randomUUID().toString();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.createdAt = LocalDateTime.now();
	}

	public String getContactId() {
		return contactId;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	// Save contact to file
	public void saveToFile(String userEmail) {

		try (java.io.FileWriter writer =
				new java.io.FileWriter("contacts.txt", true)) {

			writer.write(userEmail + "," +
					contactId + "," +
					name + "," +
					phone + "," +
					email + "," +
					createdAt + "\n");

			System.out.println("Contact saved successfully!");

		} catch (Exception e) {
			System.out.println("Error saving contact: " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Contact ID: " + contactId +
				"\nName: " + name +
				"\nPhone: " + phone +
				"\nEmail: " + email +
				"\nCreated At: " + createdAt;
	}

	// This method shows all contacts of the logged-in user
	public static void viewUserContacts(String userEmail) {

		try (java.io.BufferedReader reader =
				new java.io.BufferedReader(new java.io.FileReader("contacts.txt"))) {

			String line;
			boolean found = false;

			while ((line = reader.readLine()) != null) {

				String[] data = line.split(",", 6); 
				// 6 because we have 6 fields in file

				// Safety check to avoid errors
				if (data.length == 6 && data[0].equals(userEmail)) {

					found = true;

					System.out.println("Name: " + data[2]);
					System.out.println("Phone: " + data[3]);
					System.out.println("Email: " + data[4]);
					System.out.println("Created At: " + data[5]);
				}
			}

			if (!found) {
				System.out.println("No contacts found for this user.");
			}

		} catch (java.io.FileNotFoundException e) {
			System.out.println("No contacts file found yet.");
		} catch (Exception e) {
			System.out.println("Error reading contacts: " + e.getMessage());
		}
	}

	public static void editContact(String userEmail, String contactId, String newName, String newPhone, String newEmail) {

		File inputFile = new File("contacts.txt");
		File tempFile = new File("contacts_temp.txt");

		boolean contactFound = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			String line;

			while ((line = reader.readLine()) != null) {

				String[] data = line.split(",", 6);

				// Ensure correct format
				if (data.length != 6) {
					writer.write(line + "\n");
					continue;
				}

				// Match contact to edit
				if (data[0].equals(userEmail) && data[1].equals(contactId)) {

					contactFound = true;

					// Replace fields with new values
					String updatedLine = userEmail + "," + contactId + "," +
							(newName.isEmpty() ? data[2] : newName) + "," +
							(newPhone.isEmpty() ? data[3] : newPhone) + "," +
							(newEmail.isEmpty() ? data[4] : newEmail) + "," +
							data[5];

					writer.write(updatedLine + "\n");

				} else {
					// Keep other lines as-is
					writer.write(line + "\n");
				}
			}

			if (!contactFound) {
				System.out.println("Contact ID not found for this user.");
			} else {
				System.out.println("Contact updated successfully!");
			}

		} catch (Exception e) {
			System.out.println("Error editing contact: " + e.getMessage());
			return;
		}

		// Replace old file with updated file
		if (!inputFile.delete()) {
			System.out.println("Could not delete original contacts file.");
			return;
		}
		if (!tempFile.renameTo(inputFile)) {
			System.out.println("Could not rename temp file.");
		}
	}
	// Delete a contact for a logged-in user
	public static void deleteContact(String userEmail, String contactId) {

		File inputFile = new File("contacts.txt");
		File tempFile = new File("contacts_temp.txt");

		boolean contactFound = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			String line;

			while ((line = reader.readLine()) != null) {

				String[] data = line.split(",", 6);

				// Safety check
				if (data.length != 6) {
					writer.write(line + "\n");
					continue;
				}

				// If this is the contact to delete, skip writing it
				if (data[0].equals(userEmail) && data[1].equals(contactId)) {
					contactFound = true;
					// Skip line => deletion
				} else {
					writer.write(line + "\n");
				}
			}

			if (!contactFound) {
				System.out.println("Contact ID not found for this user.");
			} else {
				System.out.println("Contact deleted successfully!");
			}

		} catch (Exception e) {
			System.out.println("Error deleting contact: " + e.getMessage());
			return;
		}

		// Replace old file with updated file
		if (!inputFile.delete()) {
			System.out.println("Could not delete original contacts file.");
			return;
		}
		if (!tempFile.renameTo(inputFile)) {
			System.out.println("Could not rename temp file.");
		}

	}

}