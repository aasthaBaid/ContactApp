package com.contacts;


import java.io.*;
import java.io.FileReader;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.*;

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
	
	public static void bulkDeleteContacts(String userEmail, List<String> contactIds) {

	    File inputFile = new File("contacts.txt");
	    File tempFile = new File("contacts_temp.txt");

	    Set<String> idSet = new HashSet<>(contactIds);  // faster lookup
	    boolean anyDeleted = false;

	    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

	        String line;

	        while ((line = reader.readLine()) != null) {

	            String[] data = line.split(",", 6);

	            if (data.length != 6) {
	                writer.write(line + "\n");
	                continue;
	            }

	            if (data[0].equals(userEmail) && idSet.contains(data[1])) {
	                anyDeleted = true;
	                continue;  // skip writing => delete
	            }

	            writer.write(line + "\n");
	        }

	        if (anyDeleted) {
	            System.out.println("Selected contacts deleted successfully.");
	        } else {
	            System.out.println("No matching contacts found.");
	        }

	    } catch (Exception e) {
	        System.out.println("Error during bulk delete: " + e.getMessage());
	        return;
	    }

	    inputFile.delete();
	    tempFile.renameTo(inputFile);
	}
	
	public static void bulkTagContacts(String userEmail, List<String> contactIds, String tag) {

	    File inputFile = new File("contacts.txt");
	    File tempFile = new File("contacts_temp.txt");

	    Set<String> idSet = new HashSet<>(contactIds);

	    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

	        String line;

	        while ((line = reader.readLine()) != null) {

	            String[] data = line.split(",", 7);

	            if (data.length < 6) {
	                writer.write(line + "\n");
	                continue;
	            }

	            if (data[0].equals(userEmail) && idSet.contains(data[1])) {

	                String updatedLine = data[0] + "," + data[1] + "," +
	                        data[2] + "," + data[3] + "," +
	                        data[4] + "," + data[5] + "," + tag;

	                writer.write(updatedLine + "\n");

	            } else {
	                writer.write(line + "\n");
	            }
	        }

	        System.out.println("Tag added to selected contacts.");

	    } catch (Exception e) {
	        System.out.println("Error tagging contacts: " + e.getMessage());
	        return;
	    }

	    inputFile.delete();
	    tempFile.renameTo(inputFile);
	}

	public static void exportContacts(String userEmail) {

	    try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"));
	         BufferedWriter writer = new BufferedWriter(
	                 new FileWriter(userEmail + "_contacts_export.txt"))) {

	        String line;
	        boolean found = false;

	        while ((line = reader.readLine()) != null) {

	            String[] data = line.split(",", 7);

	            if (data.length >= 6 && data[0].equals(userEmail)) {
	                writer.write(line + "\n");
	                found = true;
	            }
	        }

	        if (found) {
	            System.out.println("Contacts exported successfully.");
	        } else {
	            System.out.println("No contacts found to export.");
	        }

	    } catch (Exception e) {
	        System.out.println("Error exporting contacts: " + e.getMessage());
	    }
	}
	public static void addTagsToContact(String userEmail, String contactId, List<String> tagNames) {

	    File inputFile = new File("contacts.txt");
	    File tempFile = new File("contacts_temp.txt");

	    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

	        String line;

	        while ((line = reader.readLine()) != null) {

	            String[] data = line.split(",", 7);

	            if (data.length < 7) {
	                writer.write(line + "\n");
	                continue;
	            }

	            if (data[0].equals(userEmail) && data[1].equals(contactId)) {

	                Set<Tag> tagSet = new HashSet<>();

	                // Load existing tags
	                if (!data[6].isEmpty()) {
	                    String[] existing = data[6].split("\\|");
	                    for (String t : existing) {
	                        tagSet.add(new Tag(t));
	                    }
	                }

	                // Add new tags
	                for (String tagName : tagNames) {
	                    tagSet.add(new Tag(tagName));
	                }

	                // Convert Set back to string
	                StringBuilder updatedTags = new StringBuilder();
	                for (Tag t : tagSet) {
	                    if (updatedTags.length() > 0) {
	                        updatedTags.append("|");
	                    }
	                    updatedTags.append(t.getName());
	                }

	                String updatedLine = data[0] + "," + data[1] + "," +
	                        data[2] + "," + data[3] + "," +
	                        data[4] + "," + data[5] + "," + updatedTags;

	                writer.write(updatedLine + "\n");

	            } else {
	                writer.write(line + "\n");
	            }
	        }

	        System.out.println("Tags applied successfully.");

	    } catch (Exception e) {
	        System.out.println("Error applying tags: " + e.getMessage());
	        return;
	    }

	    inputFile.delete();
	    tempFile.renameTo(inputFile);
	}
	public static void removeTagsFromContact(String userEmail, String contactId, List<String> tagNames) {

	    File inputFile = new File("contacts.txt");
	    File tempFile = new File("contacts_temp.txt");

	    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

	        String line;

	        while ((line = reader.readLine()) != null) {

	            String[] data = line.split(",", 7);

	            if (data.length < 7) {
	                writer.write(line + "\n");
	                continue;
	            }

	            if (data[0].equals(userEmail) && data[1].equals(contactId)) {

	                Set<Tag> tagSet = new HashSet<>();

	                if (!data[6].isEmpty()) {
	                    String[] existing = data[6].split("\\|");
	                    for (String t : existing) {
	                        tagSet.add(new Tag(t));
	                    }
	                }

	                for (String tagName : tagNames) {
	                    tagSet.remove(new Tag(tagName));
	                }

	                StringBuilder updatedTags = new StringBuilder();
	                for (Tag t : tagSet) {
	                    if (updatedTags.length() > 0) {
	                        updatedTags.append("|");
	                    }
	                    updatedTags.append(t.getName());
	                }

	                String updatedLine = data[0] + "," + data[1] + "," +
	                        data[2] + "," + data[3] + "," +
	                        data[4] + "," + data[5] + "," + updatedTags;

	                writer.write(updatedLine + "\n");

	            } else {
	                writer.write(line + "\n");
	            }
	        }

	        System.out.println("Tags removed successfully.");

	    } catch (Exception e) {
	        System.out.println("Error removing tags: " + e.getMessage());
	        return;
	    }

	    inputFile.delete();
	    tempFile.renameTo(inputFile);
	}
}