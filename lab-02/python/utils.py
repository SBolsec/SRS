import getpass
from os import path
import pickle

STORAGE_PATH = "./.storage.pkl"

def save_users(users):
    with open(STORAGE_PATH, "wb") as fp:
        pickle.dump(users, fp)


def load_users():
    if path.exists(STORAGE_PATH) is False:
        return {}

    with open(STORAGE_PATH, "rb") as fp:
        return pickle.load(fp)


def get_confirmed_pass(repeat=1, prompt1="Password: ", prompt2="Repeat Password: "):
    pprompt = lambda: (getpass.getpass(prompt1), getpass.getpass(prompt2))

    p1, p2 = pprompt()
    repeat -= 1
    while p1 != p2 and repeat > 0:
        print("Paswords do not match. Try again.")
        p1, p2 = pprompt()
        repeat -= 1

    return p1 if p1 == p2 else None

