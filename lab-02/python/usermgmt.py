import bcrypt
import utils
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
        user.force_password_change = true
        utils.save_users(users)
    print("User will be requested to change password on next login.")


def delete_user(username):
    users = utils.load_users()
    users.pop(username, None)
    utils.save_users(users)
    print("User successfully removed.")


# TODO: main