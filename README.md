# ContactApp


## UC1: User Registration

1. Created `com.userregistration` package.  
2. Included `User`, `Main`, `FreeUser`, `PremiumUser`, and `ValidationException` classes.  
3. Allows users to register using name, phone number, email, and password.  
4. Password is hashed before storing in a text file.

### Concepts Applied:

- Encapsulation (`User.java`)
- Abstraction (`User.java`)
- Inheritance (`FreeUser.java`, `PremiumUser.java`)
- Polymorphism (`User.java`, `Main.java`)
- Password hashing using `MessageDigest`
- File handling (`FileWriter`)
- Exception handling

## UC2: User Authentication

1. Created `com.userauthentication` package.  
2. Included `Authentication.java`, `BasicAuthentication.java`, `Session.java`, and `SessionManager.java`.  
3. Allows registered users to login using email and password.  
4. Creates a session after successful authentication.

### Concepts Applied:

- Interface implementation (`Authentication`)
- Polymorphism (different authentication behavior)
- Password hashing verification
- Session creation and management
- Singleton concept (`SessionManager`)
- Optional class for safe login handling

## UC3: User Profile Management

1. Created `com.userprofile` package.  
2. Included `UserProfileManager.java`.  
3. Allows logged-in users to:
   - Update full name
   - Update phone number
   - Change password
4. Updated data is saved back into the file.

### Concepts Applied:

- Encapsulation (profile updates through methods)
- Data validation before updating
- Secure password change with old password verification
- File rewriting for updating stored data
- JavaBeans conventions (getters/setters)

## UC4: Create Contact

1. Created `com.contactmanagement` package.  
2. Included `Contact.java`.  
3. Logged-in users can create contacts.  
4. Each contact includes:
   - Unique ID (UUID)
   - Name
   - Phone
   - Email
   - Created timestamp
5. Contacts are stored in `contacts.txt` linked to the user’s email.

### Concepts Applied:

- Object composition
- UUID for unique identification
- `LocalDateTime` for timestamp
- File handling using `FileWriter`
- Data persistence


## UC5: View Created Contacts

1. Extended `Contact.java` to support viewing contacts.  
2. Logged-in users can view only their own contacts.  
3. Contacts are read from `contacts.txt`.  
4. Data is filtered using logged-in user’s email.

### Concepts Applied:

- File reading using `BufferedReader`
- String parsing using `split()`
- Conditional filtering
- Looping structures
- Separation of concerns (Contact handles contact logic)



## Technologies Used

- Java
- OOP Concepts
- File Handling (Text Files)
- UUID
- LocalDateTime
- MessageDigest (SHA-256)





