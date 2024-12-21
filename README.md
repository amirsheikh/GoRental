# GoRental: Car Rental Service

GoRental is a car rental web application built using **Spring Boot**. It enables users to browse available rental cars, make reservations, and manage their accounts. The application uses **PostgreSQL** with the **PostGIS** extension for geographic data and **JWT** for user authentication.

## Proposal

For more details on the project's assessment, roadmap, MVP phases, and success metrics, please refer to the [full proposal](https://github.com/amirsheikh/GoRental/tree/master/proposal).

## Technologies Used

- **Backend Framework**: Spring Boot
- **Database**: PostgreSQL with PostGIS extension for geographic data
- **Authentication**: JWT (JSON Web Tokens)
- **API Documentation**: Swagger
- **Build Tools**: Gradle/Maven
- **Containerization**: Docker & Docker Compose
- **Testing**: JUnit and Mockito
- **Geographic Data**: PostGIS for spatial queries
- **Front-End Support**: Swagger UI for API exploration

## Database Schema (UML Diagram)

```plaintext
+------------------------+
|       User             |
+------------------------+
| - id: Long             |
| - name: String         |
| - username: String     |
| - password: String     |
| - email: String        |
| - role: String         |
+------------------------+
        |
        | 1..*
        v
+------------------------+
|   Reservation          |
+------------------------+
| - id: Long             |
| - userId: Long         |
| - carId: Long          |
| - startTime: Date      |
| - endTime: Date        |
+------------------------+
        ^
        |
        | 1..*
+------------------------+
|       Car              |
+------------------------+
| - id: Long             |
| - model: String        |
| - licensePlate: String |
| - location: Point      |
| - brand: Enum          |
+------------------------+
```

- **User**: Represents the system users.
- **Reservation**: Maps users to their car rentals with dates and statuses.
- **Car**: Represents available rental cars, their details, and geographic locations.

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
