package com.userregistration;

import java.util.*;
import java.io.*;
import com.userauthentication.Authentication;
import com.userauthentication.BasicAuth;
import com.userprofile.UserProfileManager;
import com.contacts.CreateContacts;
import com.search.ContactFilterService;
import com.search.FilterService;
import com.search.SearchService;
import com.search.UserSearchService;

public class Main {

	public static void main(String[] args) {

		// Scanner to read input from user
		Scanner scanner = new Scanner(System.in);

		// Authentication object
		Authentication auth = new BasicAuth();

		// This stores currently logged-in user
		Optional<User> loggedInUser = Optional.empty();

		// Controls program loop
		boolean running = true;

		while (running) {

			System.out.println("\n===== MENU =====");

			// If user is NOT logged in
			if (loggedInUser.isEmpty()) {

				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("3. Exit");
				System.out.print("Choose option: ");

				int choice = Integer.parseInt(scanner.nextLine());

				try {

					switch (choice) {

					case 1:
						// Registration process
						System.out.print("Enter Email: ");
						String email = scanner.nextLine();

						System.out.print("Enter Password: ");
						String password = scanner.nextLine();

						System.out.print("Enter Full Name: ");
						String name = scanner.nextLine();

						System.out.print("Enter Phone Number: ");
						String phone = scanner.nextLine();

						System.out.print("Free or Premium (f/p): ");
						String type = scanner.nextLine();

						User newUser;

						if (type.equalsIgnoreCase("f")) {
							newUser = new FreeUser(email, password, name, phone);
						} else {
							newUser = new PremiumUser(email, password, name, phone);
						}

						newUser.saveToFile();
						System.out.println("User registered successfully!");
						break;

					case 2:
						// Login process
						System.out.print("Enter Email: ");
						String loginEmail = scanner.nextLine();

						System.out.print("Enter Password: ");
						String loginPassword = scanner.nextLine();

						loggedInUser = auth.login(loginEmail, loginPassword);

						if (loggedInUser.isPresent()) {
							System.out.println("Login successful! Welcome "
									+ loggedInUser.get().getFullName());
						} else {
							System.out.println("Invalid credentials!");
						}
						break;

					case 3:
						// Exit program
						running = false;
						System.out.println("Exiting system...");
						break;

					default:
						System.out.println("Invalid choice!");
					}

				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			// If user IS logged in
			else {

				System.out.println("1. View Profile Details");
				System.out.println("2. Update Profile");
				System.out.println("3. Create Contact");
				System.out.println("4. View Contact");
				System.out.println("5. Edit Contact");
				System.out.println("6. Delete Contact");
				System.out.println("7. Logout");
				System.out.println("8. Bulk Operations");
				System.out.println("9. Search");
				System.out.println("10. Filter Users");
				System.out.println("11. Manage Tags");
				System.out.println("12. Apply Multiple Tags");
				System.out.print("Choose option: ");

				int choice = Integer.parseInt(scanner.nextLine());

				try {

					User user = loggedInUser.get();
					UserProfileManager profileManager = new UserProfileManager(user);

					switch (choice) {

					case 1:
						// Display user details
						System.out.println("\nUser Details:");
						System.out.println("Email: " + user.getEmail());
						System.out.println("Name: " + user.getFullName());
						System.out.println("Phone: " + user.getPhoneNumber());
						System.out.println("Type: " + user.getUserType());
						break;

					case 2:
						// Update profile submenu
						System.out.println("1. Update Name");
						System.out.println("2. Update Phone");
						System.out.println("3. Change Password");
						System.out.print("Choose option: ");

						int updateChoice = Integer.parseInt(scanner.nextLine());

						switch (updateChoice) {

						case 1:
							System.out.print("Enter New Name: ");
							String newName = scanner.nextLine();
							profileManager.updateFullName(newName);
							break;

						case 2:
							System.out.print("Enter New Phone: ");
							String newPhone = scanner.nextLine();
							profileManager.updatePhoneNumber(newPhone);
							break;

						case 3:
							System.out.print("Enter Old Password: ");
							String oldPass = scanner.nextLine();

							System.out.print("Enter New Password: ");
							String newPass = scanner.nextLine();

							profileManager.changePassword(oldPass, newPass);
							break;

						default:
							System.out.println("Invalid update option!");
						}
						break;

					case 3:
						// Create new contact
						System.out.print("Enter Contact Name: ");
						String contactName = scanner.nextLine();

						System.out.print("Enter Contact Phone: ");
						String contactPhone = scanner.nextLine();

						System.out.print("Enter Contact Email: ");
						String contactEmail = scanner.nextLine();

						CreateContacts contact = new CreateContacts(
								contactName,
								contactPhone,
								contactEmail
								);

						// Save contact linked to logged-in user
						contact.saveToFile(user.getEmail());
						break;

					case 4:
						// View contacts of logged-in user
						CreateContacts.viewUserContacts(user.getEmail());
						break;

					case 5:
						// Edit contact
						System.out.print("Enter Contact ID to edit: ");
						String contactId = scanner.nextLine();

						System.out.print("Enter New Name (leave blank to keep unchanged): ");
						String newName = scanner.nextLine();

						System.out.print("Enter New Phone (leave blank to keep unchanged): ");
						String newPhone = scanner.nextLine();

						System.out.print("Enter New Email (leave blank to keep unchanged): ");
						String newEmail = scanner.nextLine();

						CreateContacts.editContact(user.getEmail(), contactId, newName, newPhone, newEmail);
						break;

					case 6:
						// Delete contact
						System.out.print("Enter Contact ID to delete: ");
						String deleteId = scanner.nextLine();

						System.out.print("Are you sure you want to delete this contact? (y/n): ");
						String confirm = scanner.nextLine();

						if (confirm.equalsIgnoreCase("y")) {
							CreateContacts.deleteContact(user.getEmail(), deleteId);
						} else {
							System.out.println("Deletion cancelled.");
						}
						break;

					case 7:
						// Logout
						auth.logout(user);
						loggedInUser = Optional.empty();
						System.out.println("Logged out successfully.");
						break;
					case 8:

						System.out.println("1. Bulk Delete");
						System.out.println("2. Bulk Tag");
						System.out.println("3. Export Contacts");
						System.out.print("Choose option: ");

						int bulkChoice = Integer.parseInt(scanner.nextLine());

						switch (bulkChoice) {

						case 1:
							System.out.println("Enter Contact IDs separated by comma:");
							String idsInput = scanner.nextLine();
							List<String> idsToDelete = Arrays.asList(idsInput.split(","));
							CreateContacts.bulkDeleteContacts(user.getEmail(), idsToDelete);
							break;

						case 2:
							System.out.println("Enter Contact IDs separated by comma:");
							String idsTagInput = scanner.nextLine();
							List<String> idsToTag = Arrays.asList(idsTagInput.split(","));

							System.out.print("Enter Tag: ");
							String tag = scanner.nextLine();

							CreateContacts.bulkTagContacts(user.getEmail(), idsToTag, tag);
							break;

						case 3:
							CreateContacts.exportContacts(user.getEmail());
							break;

						default:
							System.out.println("Invalid bulk option.");
						}
						break;
					case 9:

						System.out.println("Search By:");
						System.out.println("1. Name");
						System.out.println("2. Email");
						System.out.println("3. Phone");
						System.out.print("Choose option: ");

						int searchChoice = Integer.parseInt(scanner.nextLine());
						String searchType = "";

						switch (searchChoice) {
						case 1: searchType = "name"; break;
						case 2: searchType = "email"; break;
						case 3: searchType = "phone"; break;
						default:
							System.out.println("Invalid option.");
						}

						if (!searchType.isEmpty()) {

							System.out.print("Enter keyword: ");
							String keyword = scanner.nextLine();

							SearchService<User> searchService = new UserSearchService();
							List<User> results = searchService.search(keyword, searchType);

							if (results.isEmpty()) {
								System.out.println("No matching users found.");
							} else {
								System.out.println("\nSearch Results:");
								for (User u : results) {
									System.out.println(u);
								}
							}
						}

						break;
					case 10:

					    System.out.println("Filter By:");
					    System.out.println("1. User Type");
					    System.out.println("2. Sort by Name (A-Z)");
					    System.out.println("3. Phone Contains");
					    System.out.println("4. Email Contains");
					    System.out.print("Choose option: ");

					    int filterChoice = Integer.parseInt(scanner.nextLine());
					    String filterType = "";
					    String value = "";

					    switch (filterChoice) {
					        case 1:
					            filterType = "type";
					            System.out.print("Enter User Type (Free User / Premium User): ");
					            value = scanner.nextLine();
					            break;

					        case 2:
					            filterType = "name";
					            break;

					        case 3:
					            filterType = "phone";
					            System.out.print("Enter phone keyword: ");
					            value = scanner.nextLine();
					            break;

					        case 4:
					            filterType = "email";
					            System.out.print("Enter email keyword: ");
					            value = scanner.nextLine();
					            break;

					        default:
					            System.out.println("Invalid filter option.");
					    }

					    if (!filterType.isEmpty()) {

					        FilterService<User> filterService = new ContactFilterService();
					        List<User> filteredUsers = filterService.filter(null, filterType, value);

					        if (filteredUsers.isEmpty()) {
					            System.out.println("No users found.");
					        } else {
					            System.out.println("\nFiltered Results:");
					            for (User u : filteredUsers) {
					                System.out.println(u);
					            }
					        }
					    }

					    break;
					case 11:

					    System.out.println("1. Add Tag to Contact");
					    System.out.println("2. Remove Tag from Contact");
					    System.out.print("Choose option: ");

					    int tagChoice = Integer.parseInt(scanner.nextLine());

					    System.out.print("Enter Contact ID: ");
					    String tagContactId = scanner.nextLine();

					    System.out.print("Enter Tag Name: ");
					    String tagName = scanner.nextLine();

					    List<String> singleTagList = Arrays.asList(tagName);

					    if (tagChoice == 1) {
					        CreateContacts.addTagsToContact(user.getEmail(), tagContactId, singleTagList);
					    } 
					    else if (tagChoice == 2) {
					        CreateContacts.removeTagsFromContact(user.getEmail(), tagContactId, singleTagList);
					    } 
					    else {
					        System.out.println("Invalid option.");
					    }

					    break;
					case 12:

					    System.out.println("1. Add Multiple Tags");
					    System.out.println("2. Remove Multiple Tags");
					    System.out.print("Choose option: ");

					    int tagOption = Integer.parseInt(scanner.nextLine());

					    System.out.print("Enter Contact ID: ");
					    String contactId1 = scanner.nextLine();

					    System.out.print("Enter tags separated by comma: ");
					    String tagsInput = scanner.nextLine();

					    List<String> tagList = Arrays.asList(tagsInput.split(","));

					    if (tagOption == 1) {
					        CreateContacts.addTagsToContact(user.getEmail(), contactId1, tagList);
					    }
					    else if (tagOption == 2) {
					        CreateContacts.removeTagsFromContact(user.getEmail(), contactId1, tagList);
					    }
					    else {
					        System.out.println("Invalid option.");
					    }

					    break;
					default:

						System.out.println("Invalid choice!");
					}

				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		}

		scanner.close();
	}
}