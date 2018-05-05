import java.util.*;
import java.io.*;

/**
 * @author WongNgaiSum 3035380875
 * @version 1.0
 * This is an authentication module for a system that manages users’ login information.
 * It allows the user to create and modify their information.
 * It also provides the function to authenticate the user by password.
 */
public class AuthenticSystem {

    private String menu = "Welcome to the COMP2396 Authentication system!\n" +
            "1. Authenticate user\n" +
            "2. Add user record\n" +
            "3. Edit user record\n" +
            "4. Reset user password\n" +
            "What would you like to perform?\n";

    private Scanner keyboard = new Scanner(System.in);

    // for reading whole line
    private BufferedReader keyboard2 = new BufferedReader(new InputStreamReader(System.in));

    private ArrayList<User> users = new ArrayList<User>();

    private HashGenerator hash = new HashGenerator();

    private Record record = new Record();

    private User current_user = null;

    // index of current user
    private int idx = 0;

    public static void main(String[] args) {

        AuthenticSystem system = new AuthenticSystem();
        system.start();

    }

    /**
     * Start and drive the game
     */
    public void start() {

        hash.setAlgorithm("MD5");

        users = record.loadRecord("User.txt");

        System.out.println(menu);

        String input;

        try {

            do {

                System.out.print("Please enter your command (1-4, or 0 to terminate the system): ");
                input = keyboard.next();

                switch (input) {

                    case ("1"):
                        authenticateUser();
                        break;

                    case ("2"):
                        addRecord();
                        break;

                    case ("3"):
                        editRecord();
                        break;

                    case ("4"):
                        resetRecord();

                }

            } while (!input.equals("0"));

        } catch (Exception e) {
        }

        record.storeRecord(users, "User.txt");

    }

    /**
     * Authenticate user with username and password
     * @return whether user can login or not
     */
    public boolean authenticateUser() {

        //do {

            String ac = "", pw = "";
            System.out.print("Please enter your username: ");
            ac = keyboard.next();
            System.out.print("Please enter your password: ");
            pw = keyboard.next();

            int status = 0;

            for (int i = 0; i < users.size(); i++)
                if (users.get(i).getName().equals(ac)) {

                    status = users.get(i).login(hash.getHashCode(pw));  // hash the user password for authentication
                    if (status == 1) {  // login success

                        idx = i;
                        current_user = users.get(i);
                        System.out.println("Login success! Hello " + ac + "!");
                        return true;

                    }
                }

            if (status == 3) {   // login fail >= 3 times

                System.out.println("Login failed! Your account has been locked!");
                return false;

            } else if (status == 0) {   // ac not found

                System.out.println("Account not found!");
                return false;

            } else {// wrong password

                System.out.println("Login failed!");
                return false;

            }

        //} while (true);

    }

    /**
     * Add new user
     */
    public void addRecord() {

        String userName = "", password = "", fullName = "", email = "";
        long phoneNo = 0;

        System.out.print("Please enter your username: ");
        userName = keyboard.next();

        // check whether the username is already used
        for (User u : users)
            if (u.getName().equals(userName)) {

                System.out.println("User exists, please input another username.");
                return;

            }

        password = changePassword("Your", true);
        if (password.equals("")) return;

        System.out.print("Please enter your full name: ");
        try {

            fullName = keyboard2.readLine();

        } catch (Exception e) {
        }

        System.out.print("Please enter your email address: ");
        email = keyboard.next();

        System.out.print("Please enter your Phone number: ");
        try {

            phoneNo = Long.parseLong(keyboard.next());

        } catch (Exception e) {
        }

        users.add(new User(userName, hash.getHashCode(password), fullName, email, phoneNo));    // add new user
        record.storeRecord(users, "User.txt");  // sore record to file
        System.out.println("Record added successfully!");

    }

    /**
     * Edit the record of user (password, email, full name)
     */
    public void editRecord() {

        String password = "", fullName = "", email = "";

        if (authenticateUser() == false) return;    // return if cannot login (ac being locked, wrong pw, ac not found)

        password = changePassword("Your", false);  // get the new password that user wants to change
        if (password.equals("")) return;

        System.out.print("Please enter your new full name: ");
        try {

            fullName = keyboard2.readLine();    // full name may have more than 1 word

        } catch (Exception e) {
        }

        System.out.print("Please enter your new email address: ");
        email = keyboard.next();

        users.get(idx).editRecord(hash.getHashCode(password), fullName, email); // edit information
        record.storeRecord(users, "User.txt");  // store record to file
        System.out.println("Record update successfully!");

    }

    /**
     * Enable administrator to reset users' password
     */
    public void resetRecord() {

        String ac = "", pw = "";
        User admin_ac = null;
        int user_idx = -1;

        // check admin ac exists or not
        for (User u : users)
            if (u.getName().equals("administrator")) admin_ac = u;

        if (admin_ac == null) {

            System.out.println("Admin account not found! Please add an \"administrator\" account!");
            return;

        }

        // check whether can login admin account or not
        System.out.print("Please enter the password of administrator: ");

        if (admin_ac.login(hash.getHashCode(keyboard.next())) != 1) {

            System.out.println("Login failed!");    // would not lock admin account
            return;

        }

        // check user account exists or not
        System.out.print("Please enter the user account need to reset: ");
        ac = keyboard.next();

        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getName().equals(ac)) user_idx = i;

        if (user_idx == -1) {

            System.out.println("User account not found!");
            return;

        }

        pw = changePassword("The", false); // get new password
        if (pw.equals("")) return;
        users.get(user_idx).setPassword(hash.getHashCode(pw));  // store the hashed password
        record.storeRecord(users, "User.txt");  // store record to file
        System.out.println("Password update successfully!");

    }

    /**
     * Read user input and return finalised password
     * @param word "The" or "Your", used before "password", with a particularising effect
     * @param create create new account or change password
     * @return finalised new password
     */
    public String changePassword(String word, boolean create) {

        String pw = "", n = " new";

        if (create == true) n = "";

        do {

            System.out.print("Please enter " + word.toLowerCase() + n + " password: ");
            pw = keyboard.next();

            if (pw.length() == 0 || pw.equals(pw.toLowerCase()) || pw.equals(pw.toUpperCase()))
                System.out.println(word + " password has to fulfil: at least 1 small letter, 1 capital letter, 1 digit!");

            else {

                System.out.print("Please re-enter " + word.toLowerCase() + n + " password: ");

                if (keyboard.next().equals(pw)) return pw;

                System.out.print("Password not match! ");

            }

        } while (true);

    }

}
