# G-Scores - Exam Score Lookup System

A Spring Boot-based REST API for managing and analyzing exam scores with efficient caching and robust error handling.

## Requirements

- Java JDK >= 17
- Maven >= 3.6
- MySQL/MariaDB >= 8.0
- Redis >= 6.0
- Git

## Tech Stack

- Spring Boot 3
- Redis for Caching
- MySQL/MariaDB

## Development Setup

### Clone the repository:

```bash
git clone https://github.com/minh6824pro/java_intern_project_be
cd java_intern_project_be
```

### Configure application properties:

Update the src/main/resources/application.properties file with your own URL

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

# Redis Configuration
spring.redis.host=localhost
spring.redis.port=6379

# CORS Configuration
app.frontend.origin=http://localhost:5173

# Server Configuration
server.port=8080
```

### Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

## Features

🔍 Fast score lookup by student ID (SBD)  
📊 Statistical analysis of exam scores  
🏆 Top 10 Group A student rankings  
💾 Redis caching for improved performance  
✅ Comprehensive input validation  
🔒 Cross-Origin Resource Sharing (CORS) support  
⚡ Global exception handling

## Project Structure

```
java_intern_project_be/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/go/assignment/
│   │   │       ├── config/         # Configuration classes
│   │   │       ├── controller/     # REST controllers
│   │   │       ├── model/         # Entity classes
│   │   │       ├── repository/    # Data repositories
│   │   │       ├── service/       # Business logic
│   │   │       └── exception/     # Exception handlers
│   │   └── resources/
│   │       └── application.properties
│   └── test/                      # Unit tests
├── pom.xml                        # Maven dependencies
└── README.md
```

## API Endpoints

### Score Lookup

```http
GET /api/diemthi/{sbd}
```

- `sbd`: 8-digit student ID
- Returns: Student's exam scores and details

### Statistics

```http
GET /api/diemthi/statistics
```

- Returns: Statistical analysis of exam scores

### Top Performers

```http
GET /api/diemthi/top10A
```

- Returns: List of top 10 students with grade A

## Error Handling

The API implements comprehensive error handling for:

- Invalid student ID format
- Record not found
- Server errors
- Cache-related issues

## Performance Optimization

- Redis caching for frequently accessed data
- Optimized database queries
- Efficient data serialization

## Server Requirements

- CPU: 2+ cores
- RAM: 4GB minimum
- Storage: 10GB minimum
- Network: 100Mbps minimum
