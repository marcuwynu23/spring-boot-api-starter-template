# Spring Boot API Starter Template

Production-ready starter template for building REST APIs with Spring Boot.

## Highlights

- Spring Boot 3.x + Java 17
- API-only architecture (no UI resources)
- Hot reload in development via DevTools
- ORM support via Spring Data JPA (Hibernate)
- SQL variant drivers included (SQLite, PostgreSQL, MySQL, MariaDB, SQL Server)
- Test-driven baseline with `MockMvc`
- Release packaging as executable JAR

## Technology Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA (Hibernate)
- Spring Boot Test (JUnit 5, MockMvc)
- Maven

## Prerequisites

- Java 17 or newer
- Maven 3.9 or newer

## Quick Start

Run the application locally:

```bash
mvn spring-boot:run
```

Application URL:

- `http://localhost:8080`

## API Endpoints

- `GET /` - starter metadata endpoint
- `GET /api/health` - health check endpoint
- `GET /api/items` - sample ORM-backed endpoint

Quick verification:

```bash
curl http://localhost:8080/
curl http://localhost:8080/api/health
curl http://localhost:8080/api/items
```

## ORM and Database

ORM and database configuration were moved to:

- `docs/ORM.md`

## Middleware Setup

Middleware patterns and common setup were moved to:

- `docs/MIDDLEWARES.md`

## Development Workflow

### Run in watch mode

```bash
mvn spring-boot:run
```

`spring-boot-devtools` is included, so application restarts on code changes.

### Run tests

```bash
mvn test
```

Run clean test cycle:

```bash
mvn clean test
```

## Build and Release

### Build release artifact

```bash
mvn clean package
```

Generated artifact:

- `target/spring-boot-api-starter-template-0.0.1-SNAPSHOT.jar`

### Run release artifact

```bash
java -jar target/spring-boot-api-starter-template-0.0.1-SNAPSHOT.jar
```

## Run with Containers

This project includes a `Dockerfile` for containerized builds and runtime.

### Docker

Build image:

```bash
docker build -t spring-boot-api-starter-template:latest .
```

Run container:

```bash
docker run --rm -p 8080:8080 spring-boot-api-starter-template:latest
```

### Podman

Build image:

```bash
podman build -t spring-boot-api-starter-template:latest .
```

Run container:

```bash
podman run --rm -p 8080:8080 spring-boot-api-starter-template:latest
```

### Verify containerized app

```bash
curl http://localhost:8080/
curl http://localhost:8080/api/health
curl http://localhost:8080/api/items
```

## Project Structure

```text
src/
  main/
    java/com/marcuwynu23/
      App.java
      controllers/
        MyController.java
  test/
    java/com/marcuwynu23/
      AppTest.java
pom.xml
```

## Customizing This Template

When creating a new project from this template, update:

- `groupId`, `artifactId`, and project metadata in `pom.xml`
- Base package path under `src/main/java`
- API routes, request/response contracts, and tests

## License

Use and adapt this template for personal or commercial projects.
