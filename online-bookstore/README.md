# BookNest — Online Bookstore Management System

A Java 17 / Spring Boot / MySQL / Thymeleaf internship project.

## Features
- Customer registration and login (Spring Security + BCrypt password hashing)
- Browse and search books by title or author
- Book details, shopping cart, quantity updates and stock validation
- Checkout, order creation, order history and payment status
- Admin-only book catalogue management
- Responsive bookstore interface

## Technology
- Java 17, Spring Boot 3.5, Spring MVC
- Spring Data JPA / Hibernate
- Spring Security
- MySQL
- Thymeleaf, HTML, CSS

## Run locally
1. Install JDK 17+ and Maven.
2. Start MySQL and create a database (or allow the configured URL to create it):
   ```sql
   CREATE DATABASE online_bookstore;
   ```
3. Set database credentials if needed:
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
4. From this project directory, run:
   ```bash
   mvn spring-boot:run
   ```
5. Open http://localhost:8080 and register a customer account.

## Admin account
For safety, public self-registration always creates a CUSTOMER. To create an administrator, register normally, then update the role directly in MySQL:
```sql
UPDATE customers SET role = 'ADMIN' WHERE email = 'your-email@example.com';
```
Log out and log in again. Admin book management is at `/admin/books`.

## Payment integration note
The checkout currently uses a clearly labelled **demo payment** action. It marks an order as PAID without charging money. This keeps the project runnable without merchant credentials. For a real deployment, replace this demo endpoint with a server-side Razorpay/Stripe order + signature verification flow; never trust a payment-success callback from the browser alone.

## Important production hardening
This is an internship/demo foundation, not a production commerce system. Before deployment, add CSRF-safe payment-provider integration, address/shipping and tax calculations, inventory locking for concurrent checkouts, order cancellation/refunds, rate limiting, email verification/password reset, database migrations, audit logging, and automated integration tests. Configure secrets through environment variables and use HTTPS.
