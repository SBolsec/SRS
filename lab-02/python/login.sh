#!/bin/bash

# Ova skripta pokrece login alat. Prima jedan argument, korisnicko ime.

# Za pokretanje potreban je python paket bcrypt "https://pypi.org/project/bcrypt/"
# Za instalaciju bcrypt paketa potrebno je izvrsiti sljedece naredbe:
# sudo apt-get install build-essential libffi-dev python-dev
# pip install bcrypt

if [ "$#" -ne 1 ]; then
  echo "Potrebno je predati jedan argument! Taj argument je korisnicko ime!"
  echo "Primjer: ./login.sh test"
  exit 1
fi

python3 ./src/login.py "$1"