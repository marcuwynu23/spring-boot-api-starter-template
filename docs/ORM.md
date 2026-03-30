# ORM Guide (Spring Boot + JPA)

This project uses Spring Data JPA (Hibernate) for ORM.

## What is ORM?

ORM (Object-Relational Mapping) maps Java classes to database tables so you can work with objects instead of writing raw SQL for common CRUD operations.

In this template:

- Entity: `src/main/java/com/marcuwynu23/models/TodoItem.java`
- Repository: `src/main/java/com/marcuwynu23/repositories/TodoItemRepository.java`
- API usage: `src/main/java/com/marcuwynu23/controllers/TodoController.java`
- DB config: `src/main/resources/application.properties`

## Core Flow

1. Define an entity with JPA annotations.
2. Create a repository interface that extends `JpaRepository`.
3. Inject repository into controller/service.
4. Configure datasource + JPA properties.
5. Run app and verify endpoints.

## Entity Example

`TodoItem` is mapped to table `todo_items`:

- `@Entity`
- `@Table(name = "sample_items")`
- `@Id` + `@GeneratedValue`

## Repository Example

`TodoItemRepository` extends:

```java
JpaRepository<TodoItem, Long>
```

Built-in methods:

- `findAll()`
- `findById(id)`
- `save(entity)`
- `deleteById(id)`

You can add custom methods by naming convention, e.g.:

```java
List<TodoItem> findByCompleted(boolean completed);
```

## Todo CRUD ORM Mapping

The current Todo API endpoints are backed by `TodoItemRepository`:

- `GET /api/todos` -> `findAll()`
- `GET /api/todos/{id}` -> `findById(id)`
- `POST /api/todos` -> `save(todo)`
- `PUT /api/todos/{id}` -> `save(existing)`
- `PATCH /api/todos/{id}/toggle` -> `save(existing)`
- `DELETE /api/todos/{id}` -> `deleteById(id)`

This gives a full CRUD flow using Spring Data JPA with minimal boilerplate.

## Default Database (SQLite)

Current default in `application.properties`:

- `spring.datasource.url=jdbc:sqlite:./app.db`
- `spring.datasource.driverClassName=org.sqlite.JDBC`
- `spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect`
- `spring.jpa.hibernate.ddl-auto=update`

Notes:

- SQLite file is created at `./app.db`.
- Delete `app.db` to reset local data.

## Switching SQL Variant

This template also includes PostgreSQL, MySQL, MariaDB, and SQL Server drivers.
Override datasource settings with environment variables.

Example (PostgreSQL):

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/appdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

Example (MySQL):

```bash
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/appdb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=secret
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

Example (MariaDB):

```bash
SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/appdb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=secret
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

Example (SQL Server):

```bash
SPRING_DATASOURCE_URL=jdbc:sqlserver://localhost:1433;databaseName=appdb;encrypt=false
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=YourStrong!Passw0rd
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

## Recommended Architecture

For larger features, use:

- `controller` for HTTP layer
- `service` for business logic and transactions
- `repository` for persistence
- `dto` for request/response contracts

## Testing ORM

Current API tests are in:

- `src/test/java/com/marcuwynu23/AppTest.java`

Recommended additions:

- Repository integration tests
- Service unit tests
- Endpoint tests for create/read/delete lifecycle

## Useful Commands

Run app:

```bash
mvn spring-boot:run
```

Run tests:

```bash
mvn clean test
```

Test endpoints:

```bash
curl http://localhost:8080/
curl http://localhost:8080/api/health
curl http://localhost:8080/api/todos
```
