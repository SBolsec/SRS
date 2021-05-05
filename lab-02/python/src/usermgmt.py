import bcrypt
import utils
import argparse
from user import User


def add_user(username):
    """Adds new user to storage file."""

    # load users from disk
    users = utils.load_users()

    # check if user already exists
    user = users.get(username)
    if user is not None:
        print("Username already exists.")
        return

    # get password
    password = utils.get_confirmed_pass()
    if password is None:
        print("Password mismatch.")
        return

    # check the complexity of the new password and whether it is different from the current password
    if not utils.check_password(password):
        return

    # store hashed password
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    users[username] = User(username, hashed_password)
    utils.save_users(users)
    print("User {} successfully added.".format(username))


def change_user_password(username):
    """Changes password of user."""

    # load users from disk
    users = utils.load_users()

    # check if user exists
    user = users.get(username)
    if user is None:
        print("Username does not exist.")
        return

    # get password
    password = utils.get_confirmed_pass()
    if password is None:
        print("Password mismatch")
        return

    # check the complexity of the new password and whether it is different from the current password
    if not utils.check_password(password, user.password):
        return

    # store hashed password
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    user.password = hashed_password
    utils.save_users(users)
    print("Password change successful.")


def force_password_change(username):
    """Forces user to change password on next login."""

    # load users from disk
    users = utils.load_users()

    # check if user exists
    user = users.get(username)
    if user is not None:
        # force user to change password and save to disk
        user.force_password_change = True
        utils.save_users(users)
        print("User will be requested to change password on next login.")
    else:
        print("User does not exist.")


def delete_user(username):
    """Deletes user."""

    # load users from disk
    users = utils.load_users()

    # check if user exists
    user = users.pop(username, None)
    if user is not None:
        # save file to disk with user removed
        utils.save_users(users)
        print("User successfully removed.")
    else:
        print("User does not exist.")


def parse_arguments():
    """Parses command line arguments."""
    parser = argparse.ArgumentParser(description='SRS LAB2')
    parser.add_argument('--add', nargs=1, type=str,
                        default=None, help='Username of user to add')
    parser.add_argument('--passwd', nargs=1, type=str,
                        default=None, help='Username of user to change password')
    parser.add_argument('--forcepass', nargs=1, type=str, default=None,
                        help='Username of user to force password change')
    parser.add_argument('--delete', nargs=1, type=str,
                        default=None, help='Username of user to delete password')
    return parser.parse_args()


def main():
    """Starting point of usermgmt tool."""
    args = parse_arguments()
    if args.add is not None:
        add_user(args.add[0])
    if args.passwd is not None:
        change_user_password(args.passwd[0])
    if args.forcepass is not None:
        force_password_change(args.forcepass[0])
    if args.delete is not None:
        delete_user(args.delete[0])


if __name__ == "__main__":
    main()
