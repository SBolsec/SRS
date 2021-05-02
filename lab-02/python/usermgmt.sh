#!/bin/bash

# Ova skripta pokrece usermgmt alat. Prima dva argumenta. Prvi argument je naredba
# koja moze biti add | passwd | forcepass | del. Drugi argument je korisnicko ime.

# Za pokretanje potreban je python paket bcrypt "https://pypi.org/project/bcrypt/"
# Za instalaciju bcrypt paketa potrebno je izvrsiti sljedece naredbe:
# sudo apt-get install build-essential libffi-dev python-dev
# pip install bcrypt

if [ "$#" -ne 2 ]; then
  echo "Potrebno je predati dva argumenta!"
  echo "Prvi argument moze biti jedan od sljedecih opcija: add | passwd | forcepass | del"
  echo "Drugi argument je korisnicko ime"
  echo "Primjer1: ./usermgmt.sh add test"
  echo "Primjer2: ./usermgmt.sh passwd test"
  echo "Primjer3: ./usermgmt.sh forcepass test"
  echo "Primjer4: ./usermgmt.sh del test"
  exit 1
fi

if [ "$1" != "add" -a "$1" != "passwd" -a "$1" != "forcepass" -a "$1" != "del" ]; then
  echo "Prvi argument moze biti jedan od sljedecih opcija: add | passwd | forcepass | del"
  exit 1
fi

if [ "$1" == "del" ]; then
  python3 ./src/usermgmt.py --delete "$2"
  exit 0
fi

python3 ./src/usermgmt.py --"$1" "$2"