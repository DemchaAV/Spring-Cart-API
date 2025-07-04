# Spring Cart API

A production‑ready RESTful service for managing products, shopping carts and their items, built with **Spring Boot 3.4.x**.

![Java](https://img.shields.io/badge/Java-17%2B-blue.svg) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.x-brightgreen.svg) ![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

---

## Table of Contents

1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Getting Started](#getting-started)

   1. [Prerequisites](#prerequisites)
   2. [Clone & Configure](#clone--configure)
   3. [Run Locally](#run-locally)
   4. [Run with Docker / Compose](#run-with-docker--compose)
4. [API Reference](#api-reference)
5. [Database Migration](#database-migration)
6. [Testing](#testing)
7. [Common Issues & Tips](#common-issues--tips)
8. [Contributing](#contributing)
9. [License](#license)

---

## Features

* **CRUD** operations for products, carts, and cart items.
* **JWT authentication** with role‑based authorization (admin / user).
* **Validation** using Jakarta Bean Validation.
* **Pagination** & **filtering** query parameters.
* **Rate limiting** (10 requests / 10 seconds) on product catalogue.
* **OpenAPI 3** documentation via Swagger UI.
* **Flyway** database migrations.
* **MapStruct** DTO mapping & **Lombok** boilerplate reduction.
* Environment configuration with **spring‑dotenv**.
* Ready‑to‑use **Dockerfile** & example **docker‑compose.yml**.

## Tech Stack

| Layer          | Technology                     |
| -------------- | ------------------------------ |
| Language       | **Java 17**                    |
| Framework      | **Spring Boot 3.4.5**          |
| Build Tool     | Maven                          |
| Database       | MySQL 8.x                      |
| Migrations     | Flyway                         |
| Auth           | Spring Security + JWT (jjwt)   |
| Docs           | springdoc‑openapi / Swagger UI |
| Object Mapping | MapStruct                      |
| Boilerplate    | Lombok                         |

---

## Getting Started

### Prerequisites

```bash
# Recommended versions
java --version   # 17+
mvn -v           # Maven 3.9+
mysql --version  # 8+
```

### Clone & Configure

```bash
git clone https://github.com/DemchaAV/Spring-Cart-API.git
cd Spring-Cart-API
cp .env.example .env   # adjust credentials
```

> **Environment variables (excerpt)**
>
> | Key           | Example                                 | Purpose                |
> | ------------- | --------------------------------------- | ---------------------- |
> | `DB_URL`      | `jdbc:mysql://localhost:3306/store_api` | JDBC connection string |
> | `DB_USERNAME` | `root`                                  | DB user                |
> | `DB_PASSWORD` | `secret`                                | DB password            |
> | `JWT_SECRET`  | `change_me`                             | HS256 signing key      |

### Run Locally

```bash
mvn spring-boot:run
# Swagger UI → http://localhost:8080/swagger-ui.html
```

### Run with Docker / Compose

Build a standalone image:

```bash
docker build -t spring-cart-api .
docker run -p 8080:8080 --env-file .env spring-cart-api
```

Or spin everything up with Compose:

```yaml
version: "3.8"
services:
  api:
    build: .
    ports:
      - "8080:8080"
    env_file: .env
    depends_on:
      - mysql
  mysql:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: store_api
    volumes:
      - db-data:/var/lib/mysql
volumes:
  db-data:
```

---

## API Reference

| Method     | Endpoint                     | Description                    |
| ---------- | ---------------------------- | ------------------------------ |
| **GET**    | `/api/v1/products`           | List / filter products         |
| **POST**   | `/api/v1/products` (admin)   | Create a new product           |
| **POST**   | `/api/v1/auth/login`         | Obtain JWT access token        |
| **POST**   | `/api/v1/carts`              | Create a cart for current user |
| **POST**   | `/api/v1/cart-products`      | Add product to cart            |
| **DELETE** | `/api/v1/cart-products/{id}` | Remove item from cart          |

Full contract is available in the Swagger UI.

---

## Database Migration

Flyway automatically applies migrations found in `src/main/resources/db/migration` at startup.

To create & apply a new migration manually:

```bash
mvn flyway:migrate -Dflyway.locations=filesystem:src/main/resources/db/migration
```

---

## Testing

```bash
mvn test
```

---

## Common Issues & Tips

> **Access Denied?** – Make sure you send the `Authorization: Bearer <token>` header.
>
> **MySQL connection errors** – Check your `.env` credentials and verify that the database container is healthy.

---

## Contributing

1. **Fork** the project and create your feature branch:

   ```bash
   git checkout -b feat/awesome
   ```
2. **Commit** your changes using conventional commits.
3. **Test**: make sure `mvn test` passes.
4. **Open a PR** describing your changes.

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## License

Distributed under the **MIT License**. See [`LICENSE`](LICENSE) for more information.

---

### Quick Start (Compose)

```bash
docker compose up --build
```

Enjoy building with Spring Cart API! 🎉
