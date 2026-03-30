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

## ORM and Database Configuration

This template ships with Spring Data JPA and supports multiple SQL variants.

Default configuration uses SQLite (file-based) for quick local startup:

- `spring.datasource.url=jdbc:sqlite:./data/app.db`
- `spring.jpa.hibernate.ddl-auto=update`
- `spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect`

### SQLite Notes

- Database file is created at `./data/app.db` on first run.
- If needed, remove the `data/` folder to reset local data.

### Switch to PostgreSQL

Use environment variables when running:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/appdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

### Switch to MySQL

```bash
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/appdb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=secret
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

### Switch to MariaDB

```bash
SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/appdb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=secret
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

### Switch to SQL Server

```bash
SPRING_DATASOURCE_URL=jdbc:sqlserver://localhost:1433;databaseName=appdb;encrypt=false
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=YourStrong!Passw0rd
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

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
