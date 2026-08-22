# Dance World API

Backend for a social network connecting the global dance community. Started with forró, but the architecture supports any dance style — bachata, salsa, zouk, and more.

## Tech Stack

- Java 21 + Spring Boot 3.5
- PostgreSQL + Flyway (migrations)
- Spring Security + JWT (authentication)
- Swagger / OpenAPI (interactive documentation)

## Running Locally

### Prerequisites
- Java 21
- PostgreSQL running locally
- Maven

### Database setup

```sql
CREATE DATABASE forrodb;
CREATE USER forrouser WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE forrodb TO forrouser;
GRANT ALL ON SCHEMA public TO forrouser;
ALTER DATABASE forrodb OWNER TO forrouser;
```

### Start the server

```bash
./mvnw spring-boot:run
```

API runs at `http://localhost:8080`

Interactive docs available at `http://localhost:8080/swagger-ui/index.html`

## API Endpoints

### Auth
- `POST /api/auth/register` — register a new user
- `POST /api/auth/login` — login and receive JWT token

### Profile
- `GET /api/profiles/me` — get my profile
- `PUT /api/profiles/me` — update my profile
- `GET /api/profiles/search?name=...&userType=...` — search by name and type

### Events
- `GET /api/events` — list all events
- `POST /api/events` — create an event
- `GET /api/events/my-events` — my events
- `GET /api/events/by-city?city=...` — search by city
- `GET /api/events/by-country?country=...` — search by country
- `GET /api/events/by-type?eventType=...` — search by type
- `POST /api/events/{id}/attendance?status=...` — mark attendance (GOING, INTERESTED, WENT)
- `GET /api/events/{id}/attendees` — list attendees

### Travel Schedule
- `POST /api/travels` — add a trip
- `GET /api/travels/my-travels` — my trips
- `GET /api/travels/by-city?city=...` — search by destination city

## User Types

- `DANCER` — looking for classes and events
- `TEACHER` — sharing classes and travel schedule
- `ARTIST` — DJ, band, or solo artist sharing shows and sets

## Project Structure
src/main/java/com/forroworld/forro_api/
├── controller/ HTTP endpoints
├── service/ business logic
├── repository/ database access
├── model/ JPA entities
├── dto/ data transfer objects
└── security/ JWT and security config


## Database Migrations

Managed by Flyway:
- V1 — users table
- V2 — profiles table and user types
- V3 — events table and social links on profile
- V4 — event_attendance table
- V5 — travel_schedule table