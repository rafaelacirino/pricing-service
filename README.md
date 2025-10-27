# Pricing Service
## Back-end con Tecnologías de Código Abierto (BETCA).
> Technical test project for a Spring Boot REST API that calculates applicable product prices based on brand, product ID, and application date. Designed following Hexagonal Architecture principles and built with open-source technologies.

---

## 📊 Project Status
[![Spring User - Tests](https://github.com/rafaelacirino/pricing-service/actions/workflows/ci.yml/badge.svg)](https://github.com/rafaelacirino/pricing-service/actions/workflows/ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=rafaelacirino_pricing-service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=rafaelacirino_pricing-service)

---

## Technical Test Requirements

This project fulfills all the requirements outlined in the technical test:

- ✅ REST API with a single GET endpoint
- ✅ Accepts `applicationDate`, `productId`, and `brandId` as query parameters
- ✅ Returns: product ID, brand ID, brand name, price list, start/end dates, final price, and currency
- ✅ Uses in-memory H2 database initialized with sample data
- ✅ Applies pricing logic based on date range and priority
- ✅ Returns only one applicable price per query
- ✅ Includes integration tests for 5 specific scenarios
- ✅ Follows Hexagonal Architecture
- ✅ Clean code with SOLID principles
- ✅ Proper exception handling and error responses
- ✅ Git version control and CI/CD pipeline
- ✅ SonarCloud integration for code quality
- ✅ README with full documentation

---

## Architecture Overview

This project is structured using **Hexagonal Architecture**:

- **Inbound Adapter**: `PriceController`
- **Application Layer**: `PriceService`, `GetPriceUseCase`
- **Outbound Port**: `PriceRepositoryPort`
- **Outbound Adapter**: `PriceRepositoryAdapter`
- **Persistence Layer**: `PriceRepository`, `PriceEntity`
- **Domain Model**: `Price`, `Money`

---

## Technologies Used

- [Java 21](https://openjdk.org/projects/jdk/21/) — Latest LTS version used for compilation
- [Spring Boot 3.5.6](https://spring.io/projects/spring-boot) — Framework for building production-ready applications
- [Spring Web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html) — RESTful web services and controllers
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/) — ORM and repository abstraction
- [H2 Database](https://www.h2database.com/html/main.html) — In-memory database for testing and development
- [Lombok](https://projectlombok.org/) — Reduces boilerplate code with annotations
- [SpringDoc OpenAPI](https://springdoc.org/) — Auto-generates Swagger UI for API documentation
- [JUnit 5](https://junit.org/junit5/) — Unit testing framework
- [Mockito](https://site.mockito.org/) — Mocking framework for unit tests
- [MockMvc](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework) — Simulates HTTP requests in integration tests
- [JaCoCo](https://www.jacoco.org/jacoco/) — Code coverage reports for tests
- [SonarCloud](https://sonarcloud.io/) — Continuous inspection of code quality and security
- [GitHub Actions](https://docs.github.com/en/actions) — CI/CD pipeline for automated builds and tests

---

## How to Run Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/rafaelacirino/inditex-pricing-service.git
   cd inditex-pricing-service
2. Run the application using Maven:
   ```bash
    ./mvnw spring-boot:run
3. Access the following resources:
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:pricingservice
- Username: ps
- Password: (leave blank)

---

## Testing

Unit tests cover the core business logic and adapters, for example:

- **PriceServiceTest**: validates price selection logic

- **PriceRepositoryAdapterTest**: ensures correct mapping and priority handling

- **PriceControllerTest**: verifies controller behavior and error handling

#### Run tests with:
1. To execute all unit and integration tests, use:
    ```bash
    ./mvnw test

Integration Tests

Integration tests validate the full request-response cycle using `MockMvc`. These tests simulate HTTP requests to the `/prices` endpoint and verify the expected behavior based on the initialized H2 data.

The following test cases are implemented:

| Test Case | Date & Time         | Product ID | Brand ID | Expected Status | Expected Price List | Expected Price |
|-----------|---------------------|------------|----------|------------------|----------------------|----------------|
| Test 1    | 2020-06-14 10:00    | 35455      | 1 (ZARA) | 200 OK           | 1                    | 35.50 EUR      |
| Test 2    | 2020-06-14 16:00    | 35455      | 1 (ZARA) | 200 OK           | 2                    | 25.45 EUR      |
| Test 3    | 2020-06-14 21:00    | 35455      | 1 (ZARA) | 404 Not Found    | —                    | —              |
| Test 4    | 2020-06-13 10:00    | 35455      | 1 (ZARA) | 404 Not Found    | —                    | —              |
| Test 5    | 2020-06-16 21:00    | 35455      | 1 (ZARA) | 200 OK           | 4                    | 38.95 EUR      |

Each test verifies:

- Correct selection of price based on date range
- Priority handling when multiple prices overlap
- Proper error response when no price is applicable

You can find these tests in the class:  
`src/test/java/com/cirino/rafaela/inditex/pricingservice/infrastructure/adapter/inbound/PriceControllerIntegrationTest.java`

---

## Screenshoots

Visual documentation and screenshots of the Swagger UI, H2 Console, and test results.
* Swagger UI
![Swagger UI](src/main/resources/assets/swaggerUI.png)


* Swagger Request
![Swagger Request](src/main/resources/assets/swaggerRequest.png)


* Swagger Response OK
![Swagger Request](src/main/resources/assets/swaggerResponse.png)


* Swagger Response 404
![Swagger NOT FOUND](src/main/resources/assets/priceNotFound.png)


* H2 Connection
![H2 Connection](src/main/resources/assets/h2Connection.png)


* H2 Table / Data
![H2 Data](src/main/resources/assets/h2Data.png)


* Code Coverage
![Sonar Results](src/main/resources/assets/sonarResult.png)

---

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/cirino/rafaela/inditex/pricingservice/
│   │       ├── application/             # Application layer: orchestrates use cases
│   │       │   ├── dto/                 # Data Transfer Objects for input/output
│   │       │   ├── ports/               # Interfaces (inbound/outbound) for hexagonal architecture
│   │       │   └── service/             # Business logic and service implementations
│   │       ├── domain/                  # Domain layer: core business models
│   │       │   ├── entity/              # JPA entities mapped to database tables
│   │       │   ├── exception/           # Custom domain exceptions
│   │       │   └── model/               # Value objects and domain-specific models
│   │       ├── infrastructure/          # Infrastructure layer: external integrations
│   │       │   ├── adapter/             # Adapters for persistence and other services
│   │       │   ├── config/              # Spring configuration classes
│   │       │   └── persistence/         # JPA repositories and database access
│   └── resources/
│       ├── assets/                      # Screenshots and static assets
│       ├── application.properties.yml   # Spring Boot configuration
│       ├── data.sql                     # Initial H2 data for testing
│       └── schema.sql                   # Database schema definition
└── test/
    └── java/com/inditex/pricing/
        ├── application/                 # Unit tests for service layer
        ├── domain/                      # Integration tests with MockMvc
        └── infrastructure/              # Unit tests for repository adapter
```
## 👩‍💻 Author:

**Rafaela Cirino**

Software Engineer | Java | Spring Boot | Hexagonal Architecture

🔗 [LinkedIn](https://linkedin.com/in/rafaelacirino)  
🔗 [GitHub](https://github.com/rafaelacirino)