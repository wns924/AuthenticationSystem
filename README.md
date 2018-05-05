# Authentication System
It is a simple authentication module for a system that manages users’ login information. 
The authentication module allows the user to create and modify their information. 
It also provides the function to authenticate the user by password.

## Start menu
When the program is executed, the following menu should be displayed:
```
Welcome to the COMP2396 Authentication system!
1. Authenticate user
2. Add user record
3. Edit user record
4. Reset user password
What would you like to perform?

Please enter your command (1-4, or 0 to terminate the system): 
```

## Hash
The system stores the hashed password instead of the plain text password.
Hash class is used in the following:
* Hash the user input password when adding a user.
* Hash the user input password for the authentication.
* Hash the user input password when the user change or reset the password.

## Add user record
```
Please enter your username: Raymond
Please enter your password: 123456
Your password has to fulfil: at least 1 small letter, 1 capital letter, 1 digit!
Please enter your password: 123456Abc
Please re-enter your password: 123456Abc
Please enter your full name: Raymond Chan
Please enter your email address: cbchan@cs.hku.hk
Please enter your Phone number: 12345678
Record added successfully!
```

## Modify user record
* To modify the user record, the user has to provide the username and password before he can edit the record.
* Allow user to change the Full name, password and email address.
```
Please enter your username: Raymond
Please enter your password: 123456Abc
Login success! Hello Raymond!
Please enter your new password: 123456Acb
Please re-enter your new password: 123456Acb
Please enter your new full name: Raymond Chan
Please enter your new email address: cbchan@cs.hku.hk
Record update successfully!
```

## Authentication
* The authentication module would validate if the user can provide correct username and password.
* When the user successfully login, the last login date will be updated.
* If a user failed to login for 3 times, his account will be locked and not allowed to login.
```
Please enter your username: Raymond
Please enter your password: 123456Abc
Login success! Hello Raymond!
```
* If the failed count is less than 3 and the user can login successfully, the failed count will reset to 0.
* If the failed count is greater than or equal to 3, the user account will be locked, i.e. the user will not be allowed to login again.
```
Please enter your username: Raymond
Please enter your password: 147852369
Login failed!
Please enter your username: Raymond
Please enter your password: 147852369
Login failed!
Please enter your username: Raymond
Please enter your password: 147852369
Login failed!
Please enter your username: Raymond
Please enter your password: 147852369
Login failed! Your account has been locked!
```

## Storing records
* The system reads a file “User.txt” when it is executed.
* If the file does not exist, the program will create a blank “User.txt” automatically.
* The system stores the record in JSON format.

The system stores the user records to a file when:
* The program terminates.
* A new user record added.
* When a user changed / reset the password.

## Reset password
* Only the “administrator” account can reset the user password.
* If the “administrator” not exists, prompt a message to ask the user to add an “administrator” account.
* The fail count will reset to 0 after the user password is changed.
* The “Account locked” should be reset to false.
```
Please enter the password of administrator: 741852Abc
Please enter the user account need to reset: raymond
Please enter the new password: 789456Abc
Please re-enter the new password: 789456Abd
Password not match! Please enter the new password: 789456Abc
Please re-enter the new password: 789456Abc
Password update successfully!
```
