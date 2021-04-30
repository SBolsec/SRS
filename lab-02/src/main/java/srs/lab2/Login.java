package srs.lab2;

import org.springframework.security.crypto.bcrypt.BCrypt;
import srs.lab2.data.User;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

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
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.print("Password: ");
            String password = sc.nextLine();
            if (BCrypt.checkpw(password, user.getPassword())) {
                loggedIn = true;
                break;
            } else {
                System.out.println("Username or password incorrect.");
            }
        }
        sc.close();

        if (!loggedIn) {
            System.exit(1);
        }

        //TODO: check for forced password change

        System.out.println("Login successful.");
    }
}
