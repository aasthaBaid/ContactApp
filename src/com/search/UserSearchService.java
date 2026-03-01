package com.search;
import java.io.*;
import java.util.*;
import com.contacts.CreateContacts;
import com.userregistration.User;
import com.userregistration.ValidationException;


public class UserSearchService implements SearchService<User>{

	@Override
	public List<User> search(String keyword, String searchType) throws ValidationException {

		List<User> users = User.loadUsersFromFile();
		List<User> result = new ArrayList<>();

		for (User user : users) {

			switch (searchType.toLowerCase()) {

			case "name":
				if (user.getFullName().equalsIgnoreCase(keyword) ||
						user.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
					result.add(user);
				}
				break;

			case "email":
				if (user.getEmail().equalsIgnoreCase(keyword) ||
						user.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
					result.add(user);
				}
				break;

			case "phone":
				if (user.getPhoneNumber().equals(keyword) ||
						user.getPhoneNumber().contains(keyword)) {
					result.add(user);
				}
				break;

			default:
				System.out.println("Invalid search type.");
			}
		}

		return result;
	}
}
