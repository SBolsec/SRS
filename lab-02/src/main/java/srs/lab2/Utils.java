package srs.lab2;

import srs.lab2.data.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility functions
 */
public class Utils {

    /** Path to the storage file on the disk */
    private static final String storagePath = "./.storage";

    /**
     * Save users to disk.
     * @param users users data to save
     * @throws IOException if there was an error while saving to disk
     */
    public static void saveUsers(Map<String, User> users) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storagePath));
        out.writeObject(users);
        out.close();
    }

    /**
     * Loads users from disk.
     * @return user data
     * @throws IOException if there was an error while reading from disk
     * @throws ClassNotFoundException if file has been tampered with so cast fails
     * @throws RuntimeException if something was added to the file on the disk
     */
    public static Map<String, User> loadUsers() throws IOException, ClassNotFoundException {
        if (!Files.exists(Path.of(storagePath))) return new HashMap<>();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storagePath));
        Map<String, User> o = (Map<String, User>) ois.readObject();
        if (ois.available() != 0) throw new RuntimeException("File has ben tampered!");
        ois.close();
        return o;
    }

    /**
     * Reads password from console that has to be repeated for confirmation.
     * @return password
     */
    public static String readConfirmedPasswordFromConsole(String prompt1, String prompt2) {
        Console cons = System.console();

        String password = String.valueOf(cons.readPassword(prompt1));
        String repeatedPassword = String.valueOf(cons.readPassword(prompt2));

        if (!password.equals(repeatedPassword))
            throw new IllegalArgumentException("Password mismatch.");

        return password;
    }

    /**
     * Reads password from console that is confirmed by repeating.
     * Handles case when the passwords mismatch: writes error message and terminates the program.
     * @param errorMessage error message to write
     * @return password
     */
    public static String getConfirmedPasswordFromConsole(String prompt1, String prompt2, String errorMessage) {
        try {
            return readConfirmedPasswordFromConsole(prompt1, prompt2);
        } catch (Exception e) {
            System.out.println(errorMessage);
            System.exit(1);
            return null;
        }
    }
}
