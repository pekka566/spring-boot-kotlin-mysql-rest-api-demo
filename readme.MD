# Simple Spring Boot, Maven, Kotlin + MySQL demo

This project is a simple Rest API with MockMvc tests.

### Stack
- Kotlin
- Spring Data JPA 
- Spring Boot
- MySQL

### Prerequisites
- JDK 1.8 or later
- Maven 3 or later
- MySQL 5 or later

### Installation

**1. Exceute following SQL statement for creating MySQL database before launching the app.** 

```sql
CREATE DATABASE IF NOT EXISTS kotlin_api_demo;
```

**2. Change MySQL username and password**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password`

### Run
```bash
mvn spring-boot:run
```

### Test
```bash
mvn test
```

See `fi.kotlin.api.demo.CategoriesWebMockTest` and `fi.kotlin.api.demo.ItemsWebMockTest` for operations that the Api supports. You can use Postman or any other Rest client for testing too.