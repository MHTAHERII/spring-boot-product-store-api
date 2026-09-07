# 🛒 Product Store RESTful API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x%20%2F%204.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Security-Spring%20Security%20%2B%20JWT-blue.svg)](https://spring.io/projects/spring-security)
[![Swagger / OpenAPI](https://img.shields.io/badge/API%20Docs-Swagger%20%2F%20OpenAPI%203-85EA2D.svg)](http://localhost:8080/swagger-ui/index.html)
[![Actuator](https://img.shields.io/badge/Monitoring-Spring%20Boot%20Actuator-blueviolet.svg)](http://localhost:8080/actuator)
[![Tests](https://img.shields.io/badge/Tests-JUnit%205%20%2B%20Mockito-25A162.svg)](https://junit.org/junit5/)
[![Database](https://img.shields.io/badge/Database-PostgreSQL-336791.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A production-ready and secure backend RESTful API for **Product Catalog & Inventory Management** built with **Java 21**, **Spring Boot**, **Spring Security**, **JWT Authentication**, and **PostgreSQL**.

---

## ✨ Features & What's New

- 🔐 **Authentication & Role-Based Authorization (RBAC)**:
  - Stateless authentication with **JWT (JSON Web Token)**.
  - Role-based endpoint protection with `ADMIN` and `USER` roles.
  - Password hashing with `BCryptPasswordEncoder`.
  - Custom `JwtFilter` integrated into the Spring Security filter chain.
- 📦 **Product Management (CRUD)**:
  - Create, Read, Update, and Delete products with role validation.
  - Validation constraints on product fields (`@NotBlank`, `@Positive`).
- 🔍 **Search, Filtering & Pagination**:
  - Filter by category, price range, and thresholds (`cheap`, `expensive`, `between`).
  - Search by keyword (`findByNameContainsIgnoreCase`).
  - Server-side **Pagination** and **Sorting** (`ASC` / `DESC`).
- 📖 **Interactive API Documentation (Swagger / OpenAPI 3)**:
  - Full OpenAPI documentation with built-in JWT Bearer authentication support.
  - Test endpoints directly from the browser via Swagger UI.
- 📊 **Monitoring & Health Checks (Spring Boot Actuator)**:
  - Real-time application health status, metrics, and environment inspection via Actuator endpoints.
- 🛡️ **Robust Error Handling**:
  - Custom domain exceptions (e.g. `ProductNotFoundException`).
  - Centralized exception handling via `@RestControllerAdvice` returning structured `ErrorResponse` (timestamp, HTTP status, message).
- 🧪 **Comprehensive Automated Testing**:
  - **Unit Testing** for service layer with **Mockito** (`@ExtendWith(MockitoExtension.class)`).
  - **Web Layer Integration Testing** with **MockMvc** (`@WebMvcTest`).

---

## 🛠️ Tech Stack

| Category | Technologies |
| :--- | :--- |
| **Language** | Java 21 (LTS) |
| **Framework** | Spring Boot (Web, Data JPA, Security, Validation, Actuator) |
| **Security** | Spring Security 6, JJWT (Java JWT `0.12.5`) |
| **Database & ORM** | PostgreSQL, Hibernate / Spring Data JPA |
| **API Documentation** | Springdoc OpenAPI (Swagger UI) 3 |
| **Testing** | JUnit 5, Mockito, MockMvc, AssertJ |
| **Monitoring** | Spring Boot Actuator |
| **Build Tool** | Apache Maven |

---

## 🚀 Getting Started

### Prerequisites
- JDK 21 or higher
- PostgreSQL (database named `productdb`)
- Maven (or use the included `./mvnw`)

### 1. Clone the Repository
```bash
git clone https://github.com/mhtaherii/spring-boot-product-store-api.git
cd spring-boot-product-store-api
```

### 2. Database Configuration
The application uses Spring Boot profiles to keep your credentials safe:
- `application.properties`: General configurations and active profile (`spring.profiles.active=local`).
- `application-local.properties` *(ignored by git)*: Put your local database credentials here:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/productdb
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 3. Run the Application
```bash
./mvnw spring-boot:run
```
The server will start on `http://localhost:8080`.

---

## 📖 API Documentation & Swagger UI

Once the application is running, access the interactive Swagger UI at:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

To authorize in Swagger UI:
1. Call `/token` or `/login` with your credentials to obtain a JWT.
2. Click the **Authorize 🔓** button at the top right of Swagger UI.
3. Enter your token (format: `Bearer <your_token>`) and click **Authorize**.

---

## 📊 Monitoring with Actuator

Spring Boot Actuator provides production-ready monitoring endpoints:

| Endpoint | Purpose |
| :--- | :--- |
| `GET /actuator` | Discover all available actuator endpoints |
| `GET /actuator/health` | Application health and database connection status |
| `GET /actuator/metrics` | Detailed application metrics (JVM, memory, HTTP) |
| `GET /actuator/info` | General application information |

---

## 📚 API Endpoints Overview

### 🔑 Authentication & Users
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/register` | Register a new user | Public |
| `POST` | `/token` / `/login` | Authenticate & receive JWT token | Public |
| `GET` | `/users` | List all users (Admin only) | `ADMIN` |

### 📦 Products (CRUD)
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `GET` | `/products` | List all products | `USER`, `ADMIN` |
| `GET` | `/products/{id}` | Get product details by ID | `USER`, `ADMIN` |
| `POST` | `/products` | Create a new product | `ADMIN` |
| `PUT` | `/products` | Update an existing product | `ADMIN` |
| `DELETE`| `/products/{id}` | Delete a product | `ADMIN` |

### 🔎 Search, Filter & Pagination
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `GET` | `/products/page?page=0&size=10` | Paginated product listing | `USER`, `ADMIN` |
| `GET` | `/products/sort?field=price` | Sort products ascending | `USER`, `ADMIN` |
| `GET` | `/products/sort-desc?field=price` | Sort products descending | `USER`, `ADMIN` |
| `GET` | `/products/search?field={name}` | Search products by name keyword | `USER`, `ADMIN` |
| `GET` | `/product/category/{category}` | Filter by category | `USER`, `ADMIN` |
| `GET` | `/products/range/{min}/{max}` | Filter by price range | `USER`, `ADMIN` |

---

## 🧪 Running Tests

Run all unit and integration tests using Maven:

```bash
./mvnw clean test
```

- **`ProductServiceTest`**: Validates business logic, repository interactions, and exception handling using **Mockito**.
- **`HelloControllerTest`**: Validates web endpoints, HTTP status codes, and JSON responses using **MockMvc**.

---

## 🔒 Security & JWT Usage

To call secured endpoints, pass the JWT token in the `Authorization` request header:

```http
GET /products HTTP/1.1
Host: localhost:8080
Authorization: Bearer <your_jwt_token>
```

---

## 📄 License
This project is licensed under the MIT License.