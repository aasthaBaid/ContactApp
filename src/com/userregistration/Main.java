package com.userregistration;

import java.util.Optional;
import java.util.Scanner;

import com.userauthentication.Authentication;
import com.userauthentication.BasicAuth;
import com.userprofile.UserProfileManager;
import com.contacts.CreateContacts;

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
                System.out.println("6. Logout");
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
                            // Logout
                            auth.logout(user);
                            loggedInUser = Optional.empty();
                            System.out.println("Logged out successfully.");
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