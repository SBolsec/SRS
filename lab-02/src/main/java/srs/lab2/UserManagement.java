package srs.lab2;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import srs.lab2.data.User;

import java.io.IOException;
import java.util.Map;

/**
 * Tool for user management.
 */
public class UserManagement {
    // command line arguments
    @Parameter(names = { "add" }, description = "Username of user to add")
    private String add; // user to add
    @Parameter(names = { "passwd" }, description = "Username of user to change password")
    private String passwd; // user to change password
    @Parameter(names = { "forcepass" }, description = "Username of user to force password change")
    private String forcepass; // user to force password change
    @Parameter(names = { "del" }, description = "Username of user to delete")
    private String del; // user to delete

    /**
     * Starting point of user management tool.
     * @param args command line arguments
     * @throws IOException if there is an error while reading or writing to disk
     * @throws ClassNotFoundException if the file on the disk has been tampered
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UserManagement main = new UserManagement();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        if (main.add != null) addUser(main.add);
        if (main.passwd != null) changeUserPassword(main.passwd);
        if (main.forcepass != null) forcePasswordChange(main.forcepass);
        if (main.del != null) deleteUser(main.del);
    }

    /**
     * Adds user to storage.
     * @param username username of user to add
     * @throws IOException if there is an error while reading or writing to disk
     * @throws ClassNotFoundException if the file on the disk has been tampered
     */
    private static void addUser(String username) throws IOException, ClassNotFoundException {
        String password = Utils.getConfirmedPasswordFromConsole("User add failed. Password mismatch.");

        Map<String, User> users = Utils.loadUsers();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        users.put(username, new User(username, hashedPassword));
        Utils.saveUsers(users);

        System.out.println("User " + username + " successfully added.");
    }

    /**
     * Changes password of user.
     * @param username username of user
     * @throws IOException if there is an error while reading or writing to disk
     * @throws ClassNotFoundException if the file on the disk has been tampered
     */
    private static void changeUserPassword(String username) throws IOException, ClassNotFoundException {
        String password = Utils.getConfirmedPasswordFromConsole("Password change failed. Password mismatch.");

        Map<String, User> users = Utils.loadUsers();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        users.put(username, new User(username, hashedPassword));
        Utils.saveUsers(users);

        System.out.println("Password change successful.");
    }

    /**
     * Forces user to change password on next login.
     * @param username username of user
     * @throws IOException if there is an error while reading or writing to disk
     * @throws ClassNotFoundException if the file on the disk has been tampered
     */
    private static void forcePasswordChange(String username) throws IOException, ClassNotFoundException {
        Map<String, User> users = Utils.loadUsers();
        //TODO: force password change
        Utils.saveUsers(users);

        System.out.println("User will be requested to change password on next login.");
    }

    /**
     * Deletes user.
     * @param username username of user
     * @throws IOException if there is an error while reading or writing to disk
     * @throws ClassNotFoundException if the file on the disk has been tampered
     */
    private static void deleteUser(String username) throws IOException, ClassNotFoundException {
        Map<String, User> users = Utils.loadUsers();
        users.remove(username);
        Utils.saveUsers(users);

        System.out.println("User successfully removed.");
    }
}
