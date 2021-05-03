import sys
import utils
import getpass
import bcrypt


def main():
    """Starting point of login tool."""

    # check command line arguments
    if len(sys.argv) != 2:
        print("Wrong number of arguments! Login tool takes only one argument, a username!")
        return

    # get user from username given in command line argument
    username = sys.argv[1]
    users = utils.load_users()
    user = users.get(username)

    # try to login
    logged_in = False
    for i in range(3):
        password = getpass.getpass("Password: ")
        if user is not None and bcrypt.checkpw(password.encode('utf-8'), user.password):
            logged_in = True
            break
        else:
            print("Username or password incorrect.")

    # if login was unsuccessful terminate the program
    if not logged_in:
        return

    # check if user is forced to change password
    if user.force_password_change:
        # get new password
        password = utils.get_confirmed_pass(1, "New password: ", "Repeat new password: ")
        if password is None:
            print("Password change failed. Password mismatch.")
            return

        # check password length
        if len(password) < 8:
            print("Password must be at least 8 characters long.")
            return

        # store new hashed password to disk
        hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
        user.password = hashed_password
        user.force_password_change = False
        utils.save_users(users)

    print("Login successful.")


if __name__ == "__main__":
    main()
