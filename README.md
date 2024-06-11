# Fermeri IoT

## Opis

Fermeri IoT je projekt namijenjen za upravljanje IoT uređajima u poljoprivredi. 
Korisnicima omogućuje pregled i upravljanje njihovim uređajima putem web aplikacije koja koristi Spring Boot za backend i HTML/CSS/JavaScript za frontend. 
Korisnici mogu postaviti minimalne i maksimalne vrijednosti temperature i vlage za svoje uređaje te pratiti te vrijednosti putem ThingsBoard sučelja.

## Značajke

- Prijava korisnika i autentifikacija pomoću ThingsBoard API-ja.
- Pregled dostupnih uređaja korisnika.
- Postavljanje i pregled atributa (minimalna i maksimalna temperatura, minimalna i maksimalna vlaga) za svaki uređaj.
- Integracija s ThingsBoard sučeljem za prikaz i upravljanje uređajima.

## Tehnologije

- Java 18
- Spring Boot 3.0.0
- HTML/CSS/JavaScript
- ThingsBoard API

## Preduvjeti

Prije nego što pokrenete projekt, provjerite imate li instalirane sljedeće alate na vašem računalu:

- [Java 18](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html)
- [Maven](https://maven.apache.org/install.html)


## Postavljanje projekta

1. Klonirajte repozitorij:
    ```sh
    git clone https://github.com/RokoK3/RokoIoT.git
    ```

2. Uđite u direktorij projekta:
    ```sh
    cd RokoIoT
    ```

3. Izgradite projekt koristeći Maven:
    ```sh
    mvn clean install
    ```

4. Pokrenite aplikaciju:
    ```sh
    ./mvnw spring-boot:run
    ```
