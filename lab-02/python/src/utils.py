import getpass
from os import path
import pickle
import bcrypt
import re


STORAGE_PATH = "./.storage.pkl"


def save_users(users):
    """Saves given users dictionary to disk."""
    with open(STORAGE_PATH, "wb") as fp:
        pickle.dump(users, fp)


def load_users():
    """Loads users dictionary from disk."""
    if path.exists(STORAGE_PATH) is False:
        return {}

    with open(STORAGE_PATH, "rb") as fp:
        return pickle.load(fp)


def get_confirmed_pass(repeat=1, prompt1="Password: ", prompt2="Repeat Password: "):
    """Prompts user to write password and confirm it be repeating it."""
    pprompt = lambda: (getpass.getpass(prompt1), getpass.getpass(prompt2))

    p1, p2 = pprompt()
    repeat -= 1
    while p1 != p2 and repeat > 0:
        print("Paswords do not match. Try again.")
        p1, p2 = pprompt()
        repeat -= 1

    return p1 if p1 == p2 else None

def check_password(password, old_password=None):
    """Checks complexity of new password and whether it is different from the old password if its provided."""
    # check password complexity
    reg = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!#%*?&]{8,50}$"
    pat = re.compile(reg)
    if not re.search(pat, password):
        print("Password change failed.")
        print("Password must be between 8 and 50 characters long, it must contain at least one uppercase Later, one digit and one special character.")
        return False

    # check if new password is same as old password
    if old_password is not None and bcrypt.checkpw(password.encode('utf-8'), old_password):
        print("Password change failed. Password can not be same as old password.")
        return False

    return True