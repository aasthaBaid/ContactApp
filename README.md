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
