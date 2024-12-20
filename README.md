# GoRental: Car Rental Service

GoRental is a car rental web application built using **Spring Boot**. It enables users to browse available rental cars, make reservations, and manage their accounts. The application uses **PostgreSQL** with the **PostGIS** extension for geographic data and **JWT** for user authentication.

## Proposal

For more details on the project's assessment, roadmap, MVP phases, and success metrics, please refer to the [full proposal](https://github.com/amirsheikh/GoRental/tree/master/proposal).

## Technologies Used

- **Backend**: Spring Boot, PostgreSQL, Redis.
- **Telegram Bot**: Telegram Bot API, Spring Integration.
- **Payment Gateway**: Stripe, PayPal (optional, depending on use case).
- **Deployment**: Docker, Kubernetes (for future scaling).
- **Monitoring/Analytics**: Google Analytics, Prometheus, Grafana.

---

### Instructions for Adding the Proposal Link:
1. Replace `link_to_your_proposal` with the actual URL or location of your proposal (if it's a file in your project repository, use a relative path to link to it).
2. You can further customize the **Features**, **Technologies Used**, or any other section based on your project specifics.

Let me know if you need more adjustments!
## Project Structure

```
├── docker-compose.yml          # Docker Compose configuration to run PostgreSQL and the application
├── src                         # Application source code
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   ├── example
│   │   │   │   │   ├── renal
│   │   │   │   │   │   ├── GoRental
│   │   │   │   │   │   │   ├── controller  # API controllers
│   │   │   │   │   │   │   ├── model       # Domain models (e.g., Car, Reservation)
│   │   │   │   │   │   │   ├── repository  # Data access layer (repositories for CRUD operations)
│   │   │   │   │   │   │   ├── service     # Service layer (business logic)
│   │   │   │   │   │   │   ├── security    # Security (JWT authentication and filters)
│   │   │   │   │   │   │   └── swagger     # Configuration of swagger
│   └── resources
│       ├── application.yml      # Application configuration (e.g., database connection, JWT settings)
└── Dockerfile                  # Dockerfile to build and run the app
```

## Prerequisites

To run the project locally, you need:

- **JDK 11** or higher
- **Docker** (for running the database container)
- **Maven** or **Gradle** (for building the project)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/GoRental.git
cd GoRental
./gradlew build
```

### 2. Configure PostgreSQL

This project uses **PostgreSQL** with **PostGIS**. You can set up the database using the provided `docker-compose.yml` file.

### 3. Start the Application and PostgreSQL

Use **Docker Compose** to start the application along with PostgreSQL. This will also set up the **PostGIS** extension for PostgreSQL.

```bash
docker-compose up --build
```

### 4. Access Swagger UI

Once the application is running, you can access **Swagger UI** to explore the available APIs.

Go to: [http://localhost:8083/swagger-ui/](http://localhost:8083/swagger-ui/)

### 5. Running Tests

You can run the tests with Maven or Gradle:

- **With Maven**:
  ```bash
  mvn clean test
  ```

- **With Gradle**:
  ```bash
  gradle test
  ```

### 6. Stopping the Application

To stop the application and containers, run:

```bash
docker-compose down
```

## License

This project is licensed under the MIT License.

