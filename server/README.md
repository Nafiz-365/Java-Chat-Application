# Chat Server

This is the backend foundation for the Java Swing chat client. It currently
provides a Spring Boot application, a health endpoint, and a versioned
PostgreSQL schema migration. Authentication, database repositories, WebSocket
messaging, and authorization will be added in the next slices.

## Requirements

- JDK 21 or newer
- Apache Maven 3.9 or newer
- PostgreSQL 15 or newer for the persistence phase

The health-only mode does not require PostgreSQL. Enable migrations only after
the database credentials are configured.

## Database setup

Create a database and application user in PostgreSQL, then configure:

```text
CHAT_DB_URL=jdbc:postgresql://localhost:5432/chat_application
CHAT_DB_USERNAME=chat_app
CHAT_DB_PASSWORD=<local-password>
CHAT_DB_MIGRATIONS_ENABLED=true
```

Flyway applies `V1__create_core_schema.sql` at startup. Do not use the
superuser for the application connection in a real deployment.

## Authentication foundation

The server now includes username policy and PBKDF2-HMAC-SHA256 password
hashing with a per-password random salt. Passwords are never logged or stored
in plaintext. The database-backed registration/login endpoints and expiring
session token service will be wired next.

## Run

```text
cd server
mvn spring-boot:run
```

Verify it from another terminal:

```text
curl http://localhost:8080/api/health
```

Do not commit `.env` files, database passwords, JWT secrets, or production
configuration. Use environment variables or a secrets manager.
