# Gladiolen Backend Setup

Deze handleiding legt uit hoe u de Gladiolen backend-applicatie kunt opzetten en configureren. Volg de stappen zorgvuldig om ervoor te zorgen dat de applicatie correct functioneert.

## Vereisten
- **Java 17** of hoger
- **Docker** en **Docker Compose**
- Een IDE of editor die geschikt is voor Java-ontwikkeling (bijv. IntelliJ IDEA, Eclipse, of VS Code met Java-extensies)

## Stappen om de backend op te zetten

### 1. Application Properties Configureren
De configuratiegegevens bevinden zich in de file `application.properties`. U moet de volgende gegevens invullen:  

```properties
# Database configuratie
spring.datasource.url=jdbc:timescale://<host>:<port>/<database>
spring.datasource.username=<database-gebruiker>
spring.datasource.password=<database-wachtwoord>
# mail properties
# vul hier je gegevens van de mailprovider in, deze zal gebruikt worden om de OTP naar de gebruikers te sturen
spring.mail.host=smtp.telenet.be
spring.mail.port=587
spring.mail.username=gladiolen@telenet.be
spring.mail.password=*****
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
#JWT
#Deze key wordt gebruikt om de JWT tokens mee te versleutelen.Dit is een HS512 key. Deze kan je zelf genereren en hier invullen
project40.gladiolen-backend.app.jwt.secret-key=****
```

#### Belangrijk:
- **Database-URL**: Vervang `<host>`, `<port>` en `<database>` door de correcte waarden voor uw omgeving.
- **Gebruikersnaam en wachtwoord**: Pas `<database-gebruiker>` en `<database-wachtwoord>` aan naar uw voorkeur.
- **JWT secret key**: Je kan een secret key generen met het volgende commando.
```bash
openssl rand -hex 64
```

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

### 3. Backend Applicatie Starten

Je kunt de applicatie starten zoals elke Java-applicatie, in je favoriete editor (bijvoorbeeld IntelliJ IDEA of VS Code). Zorg ervoor dat je Application.java uitvoert of gebruik de ingebouwde Maven-taken om de applicatie te starten.

Indien alles correct is ingesteld, draait de applicatie en kun je de API-documentatie bekijken via Swagger. Open hiervoor een browser en ga naar:
```bash
http://localhost:8080/swagger-ui/index.html
```
Hier vind je een overzicht van alle beschikbare API-endpoints en kun je deze direct testen.

## Veelvoorkomende Problemen
1. **Kan geen verbinding maken met de database**  
   Controleer of de gegevens in `application.properties` overeenkomen met die in `docker-compose.yml`.

---

Voor verdere vragen of ondersteuning, neem contact op met het ontwikkelteam.
```
Voor vragen over de app contacteer Nicolas Van Dyck via R0878921@student.thomasmore.be
```
