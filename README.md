# Spring Boot API Starter Template

Production-ready starter template for building REST APIs with Spring Boot.

## Highlights

- Spring Boot 3.x + Java 17
- API-only architecture (no UI resources)
- Hot reload in development via DevTools
- Test-driven baseline with `MockMvc`
- Release packaging as executable JAR

## Technology Stack

- Java 17
- Spring Boot
- Spring Web
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

Quick verification:

```bash
curl http://localhost:8080/
curl http://localhost:8080/api/health
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
