# PeopleDesk — Employee Management System

A Java 17 / Spring MVC / Hibernate / MySQL internship project built with Spring Boot.

## Features
- Employee CRUD: create, view, update and delete employee records
- Employee directory with search by name or employee ID
- Employee details including department, designation, contact, salary and joining date
- Employment status tracking: Active, On Leave and Inactive
- Role-based access control using Spring Security
- Input validation and duplicate employee ID/email checks
- Responsive Thymeleaf interface

## Technology stack
- Java 17
- Spring Boot and Spring MVC
- Hibernate ORM through Spring Data JPA
- MySQL
- Spring Security with BCrypt password hashing
- Thymeleaf, HTML and CSS
- Maven

## Run the project
1. Install JDK 17+ and Maven.
2. Start MySQL. The configured JDBC URL creates the database automatically if the MySQL user has permission; otherwise run:
   ```sql
   CREATE DATABASE employee_management;
   ```
3. Configure credentials using environment variables, or edit `src/main/resources/application.properties`:
   - Windows PowerShell:
     ```powershell
     $env:DB_USERNAME="root"
     $env:DB_PASSWORD="your_password"
     ```
   - macOS/Linux:
     ```bash
     export DB_USERNAME=root
     export DB_PASSWORD=your_password
     ```
4. In the project directory:
   ```bash
   mvn spring-boot:run
   ```
5. Open http://localhost:8080.

## Demo accounts

| Username | Password | Role |
|---|---|---|
| admin | Admin@12345 | ADMIN |
| hr | Hr@12345 | HR |
| viewer | Viewer@12345 | VIEWER |

The demo accounts are inserted automatically on startup if they do not already exist.

## Role permissions

| Operation | ADMIN | HR | VIEWER |
|---|:---:|:---:|:---:|
| View/search employees | Yes | Yes | Yes |
| Add employee | Yes | Yes | No |
| Edit employee | Yes | Yes | No |
| Delete employee | Yes | No | No |

## Database design
- `employees`: employeeCode, firstName, lastName, email, phone, department, designation, salary, dateOfJoining, status
- `app_users`: username, BCrypt password hash and role

Hibernate maps the Java entities to MySQL tables. The application uses `spring.jpa.hibernate.ddl-auto=update` for convenient local development.

## Production considerations
This is an internship/demo application. Before production deployment, replace demo credentials, configure secrets securely, enforce HTTPS, add audit logs and pagination, introduce database migrations, and add integration tests. Salary and personal information should be protected with stricter authorization and auditing. Disable or remove demo account seeding before deployment.
