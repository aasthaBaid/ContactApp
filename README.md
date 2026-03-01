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



## UC6: Edit Contact

1. Implemented edit functionality in `CreateContacts`.
2. Users can update name, phone, and email.
3. Contact is matched using user email and contact ID.
4. File is rewritten using a temporary file.

### Concepts Applied

- Temporary file replacement pattern
- Conditional data replacement
- File rewriting using `BufferedWriter`
- Loop control structures
- Data validation

## UC7: Delete Contact

1. Implemented delete functionality in `CreateContacts`.
2. Contact is removed based on matching user email and contact ID.
3. File is rewritten without the deleted contact.

### Concepts Applied

- Conditional file filtering
- File replacement pattern
- Safe deletion logic
- Exception handling

## UC8: Bulk Operations

1. Added support for:
   - Bulk Delete
   - Bulk Tag
   - Export Contacts
2. Users provide multiple Contact IDs separated by commas.
3. System processes all selected contacts in one operation.

### Concepts Applied

- `List<String>` handling
- `Arrays.asList()` for parsing input
- `Set` for faster lookup
- Loop-based processing
- File export functionality

## UC9: Search Users

1. Created `com.search` package.
2. Implemented `SearchService` interface.
3. Implemented `UserSearchService`.
4. Users can search by:
   - Name
   - Email
   - Phone
5. Supports partial matching.

### Concepts Applied

- Interface-based design
- Polymorphism (`SearchService<User>`)
- `equalsIgnoreCase()` and `contains()`
- Loop-based filtering
- Separation of concerns

## UC10: Basic Filtering

1. Implemented `UserFilterService`.
2. Users can:
   - Filter by user type
   - Sort by name (A-Z)
   - Filter by phone
   - Filter by email

### Concepts Applied

- Interface implementation (`FilterService`)
- `Comparator`
- `Collections.sort()`
- Case-insensitive sorting
- Method references

## UC11: Create and Manage Tags

1. Created `Tag` class in `com.contacts`.
2. Contacts can have one or multiple tags.
3. Tags are stored using `Set<Tag>` to ensure uniqueness.
4. Tags are persisted as pipe-separated values (`family|work`).

### Concepts Applied

- Composition (Contact has Tags)
- `Set` collection for uniqueness
- Overriding `equals()` and `hashCode()`
- Object relationship management
- File persistence formatting

## UC12: Apply Tags to Contacts

1. Extended tag functionality to allow:
   - Adding multiple tags
   - Removing multiple tags
2. Tags are applied using contact ID.
3. Duplicate tags are prevented automatically using `Set`.

### Concepts Applied

- `add()` and `remove()` methods in collections
- Loop-based collection processing
- Object comparison using overridden methods
- Basic collection handling
- Maintaining object relationships



