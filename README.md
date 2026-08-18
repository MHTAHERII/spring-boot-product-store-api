# 🛒 Product Store RESTful API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x%20%2F%204.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Security-Spring%20Security%20%2B%20JWT-blue.svg)](https://spring.io/projects/spring-security)
[![Database](https://img.shields.io/badge/Database-PostgreSQL-336791.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A robust and secure backend RESTful API for **Product & Inventory Management** built with **Spring Boot 4 / Java 21**, **Spring Security**, **JSON Web Tokens (JWT)**, and **PostgreSQL**.

---

## ✨ Features

- 🔐 **Authentication & Authorization**:
    - Stateless authentication with **JWT (JSON Web Token)**.
    - Role-Based Access Control (**RBAC**) with `ADMIN` and `USER` roles.
    - Password hashing using `BCryptPasswordEncoder`.
- 📦 **Product Management (CRUD)**:
    - Create, Read, Update, and Delete products with role validation.
    - Validation constraints on product fields (`@NotBlank`, `@Positive`).
- 🔍 **Search, Filtering & Pagination**:
    - Search products by name and category.
    - Filter by price ranges and price thresholds (`cheap`, `expensive`, `between`).
    - Server-side **Pagination** and **Sorting** (`ASC` / `DESC`).
- 🛡️ **Global Exception Handling**: Centralized error responses for clean API consumers.

---

## 🛠️ Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot (Web, Data JPA, Security, Validation)
- **Security:** Spring Security 6, JJWT (Java JWT `0.12.5`)
- **Database:** PostgreSQL
- **Build Tool:** Maven

---

## 🚀 Getting Started

### Prerequisites
- JDK 21 or higher installed
- Maven installed (or use included `./mvnw`)
- PostgreSQL server running

### 1. Clone the Repository
```bash
git clone https://github.com/mhtaherii/spring-boot-product-store-api.git
cd spring-boot-product-store-api