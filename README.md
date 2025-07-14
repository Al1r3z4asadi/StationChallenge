# Asynchronous Charging Session Service

This project provides a robust, asynchronous system for initiating EV charging sessions. It's designed to efficiently handle high-traffic conditions using an asynchronous queue mechanism to communicate with internal services.

## 🚧 Architecture

The system employs a decoupled, microservice architecture to ensure scalability and resilience:

```
API Request → Public API Service → RabbitMQ Queue → Session Processor → Auth Service → PostgreSQL → Client Callback
```

## 📌 Prerequisites

* Docker
* Docker Compose
* Make (optional, recommended for simplified command execution)

## 🚀 Getting Started

### 1. Initial Setup

Ensure configuration files are correctly set up:

* **Public API Service** (`public-api-service/src/main/resources/application.yml`)

```yaml
server:
  port: ${SERVER_PORT:8080}
auth-service:
  base-url: ${AUTH_SERVICE_BASE_URL:http://localhost:8081}
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:}
    username: ${SPRING_DATASOURCE_USERNAME:}
    password: ${SPRING_DATASOURCE_PASSWORD:}
  rabbitmq:
    host: ${SPRING_RABBITMQ_HOST:}
---
spring:
  config:
    activate:
      on-profile: local
  datasource:
    url: jdbc:postgresql://localhost:5432/your_db
    username: your_user
    password: your_pass
    jpa:
      properties:
        hibernate:
          dialect: org.hibernate.dialect.PostgreSQLDialect
  rabbitmq:
    host: localhost
```

* **Authorization Service** (`auth-service/src/main/resources/application.yml`)

```yaml
server:
  port: ${SERVER_PORT:8081}
```

### 2. Running the Application

Use provided `Makefile` commands from the project root:

* Build images and start services:

```shell
make up
```

* Stop and remove services:

```shell
make down
```

* View real-time logs:

```shell
make logs
```

* Full cleanup (containers, volumes, images):

```shell
make clean
```

## 📖 API Documentation

### Start a Charging Session

**Endpoint:** `POST /charging-sessions`

**Method:** `POST`

**Success Response:** `202 Accepted`

#### Request Body

| Field       | Type   | Description                                                                                               |
| ----------- | ------ | --------------------------------------------------------------------------------------------------------- |
| stationId   | UUID   | Unique identifier for the charging station.                                                               |
| driverToken | String | Token (20-80 chars): uppercase letters (A-Z), lowercase (a-z), digits (0-9), and special chars `- . _ ~`. |
| callbackUrl | String | Valid HTTP/HTTPS URL for the asynchronous callback.                                                       |

**Example Request:**

```json
{
  "stationId": "123e4567-e89b-12d3-a456-426614174000",
  "driverToken": "valid-Driver.Token_123~",
  "callbackUrl": "https://client-app.com/callback-handler"
}
```

#### Immediate Response (`202 Accepted`)

```json
{
  "status": "accepted",
  "message": "Request is being processed asynchronously. The result will be sent to the provided callback URL."
}
```

### Asynchronous Callback

Upon completion, a POST request with the result is sent to `callbackUrl`.

**Example Callback Payload:**

```json
{
  "station_id": "123e4567-e89b-12d3-a456-426614174000",
  "driver_token": "validDriverToken123",
  "status": "allowed"
}
```

Possible `status` values: `allowed`, `not_allowed`, `unknown`, or `invalid`.

## 🌐 Services and Ports

| Service               | Host Port | Description                                         |
| --------------------- | --------- | --------------------------------------------------- |
| Public API Service    | 8080      | Main entry point for API requests.                  |
| Authorization Service | 8081      | Internal service for checking authorization tokens. |
| PostgreSQL Database   | 5432      | Database for storing application data.              |
| RabbitMQ UI           | 15672     | Web interface for monitoring the message queue.     |
