package srs.lab2.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Stores user information.
 */
public class User implements Serializable {
    /** Username. */
    String username;
    /** Password. */
    String password;

    /**
     * Constructor.
     * @param username username of this user
     * @param password password of this user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns username.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks whether two users are the equal.
     * @param o object to test
     * @return true if users are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    /**
     * Returns hash code.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return String.format("(%s : %s)", username, password);
    }
}
