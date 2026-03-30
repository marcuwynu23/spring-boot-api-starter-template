# Spring Boot API Starter - Makefile

APP_NAME := spring-boot-api-starter-template
VERSION := 0.0.1-SNAPSHOT
JAR := target/$(APP_NAME)-$(VERSION).jar
MAVEN := mvn
DOCKER_IMAGE := $(APP_NAME):latest

.PHONY: help run test clean build package release verify db-reset docker-build docker-run podman-build podman-run

help:
	@echo "Available targets:"
	@echo "  make run           - Run app in dev mode (spring-boot:run)"
	@echo "  make test          - Run tests"
	@echo "  make clean         - Clean build output"
	@echo "  make build         - Clean + compile + package"
	@echo "  make package       - Package JAR (skip tests)"
	@echo "  make release       - Build production JAR"
	@echo "  make verify        - Call health endpoints (requires running app)"
	@echo "  make db-reset      - Remove local SQLite database file"
	@echo "  make docker-build  - Build Docker image"
	@echo "  make docker-run    - Run Docker container"
	@echo "  make podman-build  - Build Podman image"
	@echo "  make podman-run    - Run Podman container"

run:
	$(MAVEN) spring-boot:run

test:
	$(MAVEN) clean test

clean:
	$(MAVEN) clean

build:
	$(MAVEN) clean package

package:
	$(MAVEN) -DskipTests package

release:
	$(MAVEN) clean package -DskipTests
	@echo "Release artifact: $(JAR)"

verify:
	curl http://localhost:8080/
	curl http://localhost:8080/api/health
	curl http://localhost:8080/api/todos

db-reset:
	@echo "Removing SQLite database: app.db"
	-rm -f app.db

docker-build:
	docker build -t $(DOCKER_IMAGE) .

docker-run:
	docker run --rm -p 8080:8080 $(DOCKER_IMAGE)

podman-build:
	podman build -t $(DOCKER_IMAGE) .

podman-run:
	podman run --rm -p 8080:8080 $(DOCKER_IMAGE)
