import bcrypt
import utils
import argparse
from user import User


def add_user(username):
    password = utils.get_confirmed_pass()
    if password is None:
        print("Password mismatch.")
        return

    users = utils.load_users()
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    users[username] = User(username, hashed_password)
    utils.save_users(users)
    print("User {} successfully added.".format(username))


def change_user_password(username):
    password = utils.get_confirmed_pass()
    if password is None:
        print("Password mismatch")
        return

    users = utils.load_users()
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    users[username].password = hashed_password
    utils.save_users(users)
    print("Password change successful.")


def force_password_change(username):
    users = utils.load_users()
    user = users[username]
    if user is not None:
        user.force_password_change = True
        utils.save_users(users)
    print("User will be requested to change password on next login.")


def delete_user(username):
    users = utils.load_users()
    users.pop(username, None)
    utils.save_users(users)
    print("User successfully removed.")


def parse_arguments():
    parser = argparse.ArgumentParser(description='SRS LAB2')
    parser.add_argument('--add', nargs=1, type=str, default=None, help='Username of user to add')
    parser.add_argument('--passwd', nargs=1, type=str, default=None, help='Username of user to change password')
    parser.add_argument('--forcepass', nargs=1, type=str, default=None, help='Username of user to force password change')
    parser.add_argument('--delete', nargs=1, type=str, default=None, help='Username of user to delete password')
    return parser.parse_args()


def main():
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
