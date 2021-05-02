class User:
    def __init__(self, username, password, force_password_change=False):
        self.username = username
        self.password = password
        self.force_password_change = force_password_change

    @property
    def username(self):
        return self._username

    @username.setter
    def username(self, value):
        self._username = value

    @property
    def password(self):
        return self._password

    @password.setter
    def password(self, value):
        self._password = value

    @property
    def force_password_change(self):
        return self._force_password_change

    @force_password_change.setter
    def force_password_change(self, value):
        self._force_password_change = bool(value)

