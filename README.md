# SmartPark
Parking Management System

## Prerequisites
- Java 21

## Running
Run `./mvnw spring-boot:run` (Linux) or `.\mvnw spring-boot:run` (Windows) from the project root. The application can be accessed at localhost:8080.

## Building
1. Run `./mvnw clean package`.
1. Run `java -jar target/smartpark-0.0.1-SNAPSHOT.jar` (change the version accordingly).

## Seeding
The application uses an in-memory database (H2). On startup, a few vehicle types are seeded (see /vehicle_types endpoint for available options).

## Testing
- Run `./mvnw test`.
