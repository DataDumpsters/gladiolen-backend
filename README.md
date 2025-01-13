# Gladiolen Backend Setup

Deze handleiding legt uit hoe u de Gladiolen backend-applicatie kunt opzetten en configureren. Volg de stappen zorgvuldig om ervoor te zorgen dat de applicatie correct functioneert.

## Vereisten
- **Java 17** of hoger
- **Docker** en **Docker Compose**
- Een teksteditor voor het aanpassen van configuratiebestanden (bijv. VS Code, Nano)

## Stappen om de backend op te zetten

### 1. Application Properties Configureren
De configuratiegegevens bevinden zich in de file `application.properties`. U moet de volgende gegevens invullen:  

```properties
# Database configuratie
spring.datasource.url=jdbc:timescale://<host>:<port>/<database>
spring.datasource.username=<database-gebruiker>
spring.datasource.password=<database-wachtwoord>
```

#### Belangrijk:
- **Database-URL**: Vervang `<host>`, `<port>` en `<database>` door de correcte waarden voor uw omgeving.
- **Gebruikersnaam en wachtwoord**: Pas `<database-gebruiker>` en `<database-wachtwoord>` aan naar uw voorkeur.

Indien u wijzigingen aanbrengt in de database-gebruiker of het wachtwoord, zorg er dan voor dat u deze gegevens ook aanpast in het Docker Compose-bestand voordat u de database start (zie stap 2).

### 2. Timescale Database Opzetten
De backend maakt gebruik van een Timescale-database die eenvoudig kan worden opgezet via het meegeleverde Docker Compose-bestand.  

Voer het volgende commando uit in de rootmap van het project:  
```bash
docker-compose up -d
```

#### Aanpassen van Docker Compose:
Als u de standaard gebruikersnaam of wachtwoord in `application.properties` wijzigt, pas dit dan ook aan in `docker-compose.yml`:

```yaml
environment:
  POSTGRES_USER: <database-gebruiker>
  POSTGRES_PASSWORD: <database-wachtwoord>
  POSTGRES_DB: <database-naam>
```

### 3. Privacyverklaring Toevoegen
De applicatie vereist een PDF-bestand met de privacyregels. Dit bestand moet worden geplaatst in de map `public/documents` met de naam **`privacyverklaring.pdf`**.  

#### Stappen:
1. Maak de map `public/documents` indien deze nog niet bestaat.
2. Voeg de PDF toe aan deze map en zorg ervoor dat deze exact **`privacyverklaring.pdf`** heet.

### 4. Backend Applicatie Starten
Start de backend-applicatie door het volgende commando uit te voeren:  
```bash
./gradlew bootRun
```

Indien alles correct is ingesteld, draait de applicatie nu en kunt u deze benaderen via de opgegeven API-endpoints.

## Veelvoorkomende Problemen
1. **Kan geen verbinding maken met de database**  
   Controleer of de gegevens in `application.properties` overeenkomen met die in `docker-compose.yml`.

2. **Privacyverklaring ontbreekt**  
   Controleer of de file `privacyverklaring.pdf` in de juiste map staat (`public/documents`) en correct is genoemd.

---

Voor verdere vragen of ondersteuning, neem contact op met het ontwikkelteam.
```
