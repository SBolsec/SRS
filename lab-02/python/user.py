class User:
    """Stores user information."""

    def __init__(self, username, password, force_password_change=False):
        """Constructor."""
        self.username = username
        self.password = password
        self.force_password_change = force_password_change

    @property
    def username(self):
        """Returns the username."""
        return self._username

    @username.setter
    def username(self, value):
        """Sets the username."""
        self._username = value

    @property
    def password(self):
        """Returns the hashed password."""
        return self._password

    @password.setter
    def password(self, value):
        """Sets the hashed password."""
        self._password = value

    @property
    def force_password_change(self):
        """Flag whether the user will be forced to change the password on next login."""
        return self._force_password_change

    @force_password_change.setter
    def force_password_change(self, value):
        """Sets flag whether the user will be forced to change the password on next login."""
        self._force_password_change = bool(value)

