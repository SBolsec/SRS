#!/bin/bash

# Ova skripta pokrece demonstraciju alata pokrecuci alate

# Za pokretanje potreban je python paket bcrypt "https://pypi.org/project/bcrypt/"
# Za instalaciju bcrypt paketa potrebno je izvrsiti sljedece naredbe:
# Debian/Ubuntu: sudo apt-get install build-essential libffi-dev python-dev
# Fedora and RHEL-derivatives: sudo yum install gcc libffi-devel python-devel
# Alpine: apk add --update musl-dev gcc libffi-dev

pip install bcrypt

echo ">>> ./usermgmt.sh add sgros"
./usermgmt.sh add sgros

echo ">>> ./login.sh nepostojeci"
./login.sh nepostojeci

echo ">>> ./login.sh sgros"
./login.sh sgros

echo ">>> ./usermgmt.sh passwd sgros"
./usermgmt.sh passwd sgros

echo ">>> ./login.sh sgros"
./login.sh sgros

echo ">>> ./usermgmt.sh forcepass sgros"
./usermgmt.sh forcepass sgros

echo ">>> ./login.sh sgros"
./login.sh sgros

echo ">>> ./usermgmt.sh del sgros"
./usermgmt.sh del sgros

echo ">>> ./login.sh sgros"
./login.sh sgros