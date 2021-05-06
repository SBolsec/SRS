#!/bin/bash

# Ova skripta pokrece demonstraciju alata pokrecuci alate.

# Moguce je pokretati alate samostalno bez ove skripte.
# Svaki od alata ima pripadajucu shell skriptu sa kratkim uputama koristenja.

# Za pokretanje potreban je python paket bcrypt 'https://pypi.org/project/bcrypt/'
# Za instalaciju bcrypt paketa potrebno je izvrsiti sljedece naredbe:
# Debian/Ubuntu: sudo apt-get install build-essential libffi-dev python-dev
# Fedora and RHEL-derivatives: sudo yum install gcc libffi-devel python-devel
# Alpine: apk add --update musl-dev gcc libffi-dev

pip install bcrypt

# obrise racune stvorene prethodnim pokretanjem ove skripte
if [ -e ".storage.pkl" ]; then
  rm .storage.pkl
fi

echo ''
echo '>>> ./usermgmt.sh add sgros'
echo '### Molim Vas da unesete razlicite lozinke. ###'
./usermgmt.sh add sgros

echo ''
echo '>>> ./usermgmt.sh add sgros'
echo '### Molim Vas da unesete lozinku duljine manje od 8 znakova. ###'
./usermgmt.sh add sgros

echo ''
echo '>>> ./usermgmt.sh add sgros'
echo '### Molim Vas da unesete jednake lozinke duljine vece ili jednake 8 znakova koje NE SADRZE!! barem jedno veliko slovo, jednu znamenku i jedan specijalni znak [@$!%*#?&]. ###'
./usermgmt.sh add sgros

echo ''
echo '>>> ./usermgmt.sh add sgros'
echo '### Molim Vas da unesete jednake lozinke duljine vece ili jednake 8 znakova koje SADRZE!! barem jedno veliko slovo, jednu znamenku i jedan specijalni znak [@$!%*#?&]. ###'
./usermgmt.sh add sgros

echo ''
echo '>>> ./login.sh nepostojeci'
echo '### Unos lozinki nije bitan jer ovaj korisnik ne postoji. ###'
./login.sh nepostojeci

echo ''
echo '>>> ./login.sh sgros'
echo '### Dva puta je moguce unijeti pogresnu lozinku, treci put molim Vas da unesete ispravnu lozinku. ###'
./login.sh sgros

echo ''
echo '>>> ./usermgmt.sh passwd sgros'
echo '### Molim Vas da unesete razlicite lozinke. ###'
./usermgmt.sh passwd sgros

echo ''
echo '>>> ./usermgmt.sh passwd sgros'
echo '### Molim Vas da unesete lozinku duljine manje od 8 znakova. ###'
./usermgmt.sh passwd sgros

echo ''
echo '>>> ./usermgmt.sh passwd sgros'
echo '### Molim Vas da unesete jednake lozinke duljine vece ili jednake 8 znakova koje SADRZE!! barem jedno veliko slovo, jednu znamenku i jedan specijalni znak [@$!%*#?&]. ###'
./usermgmt.sh passwd sgros

echo ''
echo '>>> ./login.sh sgros'
echo '### Molim Vas da prvo unesete staru lozinku, a zatim novu lozinku. ###'
./login.sh sgros

echo ''
echo '>>> ./usermgmt.sh forcepass sgros'
echo '### Ovdje nije potreban unos. Korisnik ce biti prisiljen promijeniti lozinku kod login-a. ###'
./usermgmt.sh forcepass sgros

echo ''
echo '>>> ./login.sh sgros'
echo '### Molim Vas da unesete ispravnu lozinku, zatim kod promjene lozinke unesete neispravan par lozinka (razlicite lozinke, duljina < 8, nema barem jedno veliko slovo, jednu znamenku i jedan specijalni znak [@$!%*#?&]). ###'
./login.sh sgros

echo ''
echo '>>> ./login.sh sgros'
echo '### Molim Vas da unesete ispravnu lozinku, zatim kod promjene lozinke unesete istu lozinku kao kod prijave. ###'
./login.sh sgros

echo ''
echo '>>> ./login.sh sgros'
echo '### Molim Vas da unesete ispravnu lozinku, zatim kod promjene lozinke unesete ispravan par lozinka (jednake lozikne i duljina >= 8 i barem jedno veliko slovo, jedna znamenka, jedan specijalni znak [@$!%*#?&]). ###'
./login.sh sgros

echo ''
echo '>>> ./login.sh sgros'
echo '### Molim Vas da unesete novu promijenjenu loziku. ###'
./login.sh sgros

echo ''
echo '>>> ./usermgmt.sh del sgros'
echo '### Ovdje nije potreban unos. ###'
./usermgmt.sh del sgros

echo ''
echo '>>> ./login.sh sgros'
echo '### Unos nije bitan jer korisnik vise ne postoji. ###'
./login.sh sgros