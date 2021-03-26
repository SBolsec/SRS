#!/bin/bash

# Rjesenje je pisano u Javi, potreban je prevoditelj za Javu 11 i alat maven

# Ova skripta omogucava koristenje programa predajom argumenata, ili ako se ne navedu argumenti izvodi se demonstracija rada

# Prvo je potrebno prevesti izvorni kod:
if [ ! -d "./target" ]; then
  mvn compile
fi

# Ako nisu predani argumenti, izvrsava se demonstracija rada
if [ "$#" -eq 0 ]; then
  # Inicijaliziramo bazu na sljedeci nacin:
  java -cp target/classes srs.lab1.Main init masterPassword

  # Dodajemo zaporku na sljedeci nacin:
  java -cp target/classes srs.lab1.Main put masterPassword www.fer.hr zaporka

  # Dohvacamo zaporku na sljedeci nacin:
  java -cp target/classes srs.lab1.Main get masterPassword www.fer.hr

  # Ako se postavi zaporka za istu adresu, pregazit ce se u bazi:
  java -cp target/classes srs.lab1.Main put masterPassword www.fer.hr novaZaporka
  java -cp target/classes srs.lab1.Main get masterPassword www.fer.hr

  # Ako se preda pogresna glavna zaporka nije dozvoljen pristup:
  java -cp target/classes srs.lab1.Main get wrongPassword www.fer.hr

  # Moguce je spremati proizvoljan broj parova adresa i zaporka:
  java -cp target/classes srs.lab1.Main put masterPassword www.src.hr proba
  java -cp target/classes srs.lab1.Main get masterPassword www.src.hr
  java -cp target/classes srs.lab1.Main get masterPassword www.fer.hr

  # Ako se ponovno koristi naredba init, pregazit ce se prethodna baza:
  java -cp target/classes srs.lab1.Main init newMasterPassword

  # Kraj demonstracije
  exit 0
fi

# Ako su predani argumenti, poziva se program sa predanim argumentima
case "$1" in
"init")
  if [ "$#" -ne 2 ]; then
    echo "Za naredbu init potrebno je predati glavnu zaporku!"
    exit 1
  fi
  java -cp target/classes srs.lab1.Main init "$2"
  ;;
"put")
  if [ "$#" -ne 4 ]; then
    echo "Za naredbu init potrebno je predati glavnu zaporku, adresu te zaporku!"
    exit 1
  fi
  java -cp target/classes srs.lab1.Main put "$2" "$3" "$4"
  ;;
"get")
  if [ "$#" -ne 3 ]; then
    echo "Za naredbu init potrebno je predati glavnu zaporku i adresu!"
    exit 1
  fi
  java -cp target/classes srs.lab1.Main get "$2" "$3"
  ;;
*)
  echo "Nepodrzana naredba, podrzane naredbe su: init, put, get"
  ;;
esac
