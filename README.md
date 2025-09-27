# E-Commerce Auction Server System

An **e-commerce auction platform** built with **Java, Spring Boot, and Gradle**, featuring real-time bidding and an extensible architecture.

## Features

- 🛒 Auction creation & bidding
- ⚡ Real-time updates
- 👤 User management with profile & avatar upload
- 📦 Category-based browsing
- 🗄 RESTful API (OpenAPI 3.1 spec included)

---

## Tech Stack

- **Java 21+**
- **Spring Boot**
- **Gradle**
- **MySQL** (via Docker or local installation)

---

## Getting Started

### Prerequisites

- Java 21+
- Gradle (`./gradlew` included)
- MySQL running locally or via Docker

```bash
# Example: start MySQL with Docker
docker run --name auction-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=auction_db -p 3306:3306 -d mysql:8
```

### Running Locally

```bash
# Clone repository
git clone https://github.com/VicangelNik/e_commerece_auction_server_system.git
cd e_commerece_auction_server_system

# Run backend
./gradlew bootRun

# Run backend with reimport schema
./gradlew bootRun --args='--data.refresh.start=true'
```

App will start at:
```
http://localhost:8080
```

Application's Swagger:
```
http://localhost:8080/swagger-ui/index.html#/
```

---

## API Endpoints (short overview)

### Auctions
- `POST /auction/create` → create auction
- `PATCH /auction/{id}/begin` → start auction
- `GET /auction/{id}` → get auction by ID
- `DELETE /auction/{id}` → delete auction
- `GET /auction/all` → list all auctions
- `GET /auction/all/category/{categoryId}` → filter by category

### Bids
- `POST /auction/bid/add` → place bid

### Items
- `POST /auction/item/add` → add item (with image)

### Users
- `POST /user/add` → create user
- `PUT /user/{id}` → update user
- `GET /user/{id}` → get user by ID
- `GET /user/all` → list all users

### Categories
- `POST /category/add` → add category
- `GET /category/{id}` → get category by ID
- `GET /category/all` → list all categories

---

## Project Structure

```
e_commerece_auction_server_system/
 ┣ src/main/java/...        # Source code
 ┣ src/main/resources/      # Config (application.yml, schema.sql)
 ┣ build.gradle             # Gradle build file
 ┣ openapi.yaml             # API specification
 ┗ README.md                # Project documentation
```

---

## Roadmap

- [ ] Spring Security integration (authentication & roles)
- [ ] Payment processing
- [ ] Admin dashboard
- [ ] CI/CD pipeline
- [ ] Cloud deployment

---

## License

This project is licensed under the [MIT License](LICENSE).

## References

https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/jdbc.html#jdbc-auto-genereted-keys
