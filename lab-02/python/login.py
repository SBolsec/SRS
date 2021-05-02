import sys
import utils
import getpass
import bcrypt


def main():
    if len(sys.argv) != 2:
        print("Wrong number of arguments! Login tool takes only one argument, a username!")
        return

    username = sys.argv[1]
    users = utils.load_users()
    user = users[username]

    logged_in = False
    for i in range(3):
        password = getpass.getpass("Password: ")
        if user is not None and bcrypt.checkpw(password.encode('utf-8'), user.password):
            logged_in = True
            break
        else:
            print("Username or password incorrect.")

    if not logged_in:
        return

    if user.force_password_change:
        password = utils.get_confirmed_pass(1, "New password: ", "Repeat new password")
        if password is None:
            print("Password change failed. Password mismatch.")
            return

        hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
        user.password = hashed_password
        user.force_password_change = False
        utils.save_users(users)

    print("Login successfull.")


if __name__ == "__main__":
    main()
