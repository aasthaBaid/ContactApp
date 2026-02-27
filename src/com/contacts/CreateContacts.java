package com.contacts;


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
}