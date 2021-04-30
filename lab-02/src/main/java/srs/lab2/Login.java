package srs.lab2;

import org.springframework.security.crypto.bcrypt.BCrypt;
import srs.lab2.data.User;

import java.io.IOException;
import java.util.Map;

/**
 * Tool for login.
 */
public class Login {

    /**
     * Starting point of login tool.
     * @param args command line arguments
     * @throws IOException if there is an error while reading or writing to disk
     * @throws ClassNotFoundException if the file on the disk has been tampered
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments! Login tool takes only one argument, a username!");
            System.exit(1);
        }

        Map<String, User> users = Utils.loadUsers();
        User user = users.get(args[0]);

        boolean loggedIn = false;
        for (int i = 0; i < 3; i++) {
            String password = String.valueOf(System.console().readPassword("Password: "));
            if (BCrypt.checkpw(password, user.getPassword())) {
                loggedIn = true;
                break;
            } else {
                System.out.println("Username or password incorrect.");
            }
        }

        if (!loggedIn) {
            System.exit(1);
        }

        if (user.isForcePasswordChange()) {
            String password = Utils.getConfirmedPasswordFromConsole("Password change failed. Password mismatch.");
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            user.setPassword(hashedPassword);
            Utils.saveUsers(users);
        }

        System.out.println("Login successful.");
    }
}
