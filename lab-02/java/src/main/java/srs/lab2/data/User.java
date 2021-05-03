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
    /** Flag whether user will be forced to change password on next login. */
    boolean forcePasswordChange;

    /**
     * Constructor.
     * @param username username of this user
     * @param password password of this user
     */
    public User(String username, String password) {
        this(username, password, false);
    }

    /**
     * Constructor.
     * @param username username of this user
     * @param password password of this user
     * @param forcePasswordChange flag whether user will be forced to change password
     */
    public User(String username, String password, boolean forcePasswordChange) {
        this.username = username;
        this.password = password;
        this.forcePasswordChange = forcePasswordChange;
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
     * Sets the password.
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns force password change flag.
     * @return force password change flag
     */
    public boolean isForcePasswordChange() {
        return forcePasswordChange;
    }

    /**
     * Sets force password change flag.
     * @param forcePasswordChange value which to set
     */
    public void setForcePasswordChange(boolean forcePasswordChange) {
        this.forcePasswordChange = forcePasswordChange;
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
