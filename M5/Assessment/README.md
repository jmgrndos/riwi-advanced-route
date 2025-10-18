# LibroNova Library Management System

A comprehensive Java-based desktop application for managing library operations, including book and user management, loan tracking, and more.

## Features

*   **Book Management:** Add, update, delete, and search for books in the library catalog.
*   **User Management:** Manage user accounts, including different roles like Admin and Partner.
*   **Loan Management:** Track book loans, returns, and due dates.
*   **Authentication:** Secure login system for different user roles.
*   **Data Export:** Export library data to CSV files.
*   **Input Validation:** Ensures data integrity with robust input validation.

## Technologies

*   **Java:** Core programming language.
*   **JDBC:** For database connectivity.
*   **PostgreSQL:** The chosen relational database system.
*   **CSV:** For data export.

## Project Structure

The project follows a layered architecture to separate concerns and improve maintainability:

*   **`config`:** Contains database connection configuration (`ConnectionFactory`, `db.properties`).
*   **`dao`:** Data Access Object layer for database interactions (`BookDAO`, `LoanDAO`, `UserDAO`).
*   **`domain`:** Core domain models representing the application's entities (`Book`, `Loan`, `User`, `Admin`, `Partner`).
*   **`service`:** Business logic layer (`BookService`, `LoanService`, `UserService`, `AuthService`).
*   **`controller`:** Handles user input and orchestrates the application flow (`AuthController`, `AdminController`, `PartnerController`).
*   **`view`:** User interface components (`AuthView`, `MainView`, `AdminView`, `PartnerView`).
*   **`util`:** Utility classes for tasks like data export and input validation (`CSVExporter`, `InputValidator`).
*   **`app`:** Contains the main entry point of the application (`Main`).
*   **`lib`:** Contains the PostgreSQL JDBC driver.
*   **`sql`:** Contains the database schema (`scheme.sql`).

## Diagrams

The `/diagrams` directory contains UML diagrams that provide a visual overview of the project's architecture and functionality:

*   **`class_diagram.uml`:** A class diagram illustrating the relationships between the different classes in the application.
*   **`use_case_diagram.uml`:** A use case diagram outlining the interactions between users (actors) and the system.

## Installation

1.  **Prerequisites:**
    *   Java Development Kit (JDK) 8 or higher.
    *   PostgreSQL database server.

2.  **Database Setup:**
    *   Create a new database in PostgreSQL.
    *   Execute the script in `/sql/scheme.sql` to create the necessary tables.
    *   Configure your database connection in `src/config/db.properties`. Here is an example of the content of that file:

        ```properties
        db.url=jdbc:postgresql://localhost:5432/libronova_assessment_db
        db.user=postgres
        db.password=db_LocalPass1
        ```

        Make sure to update this file with your actual database credentials.

3.  **Dependencies:**
    *   Ensure the PostgreSQL JDBC driver from the `/lib` directory is included in your project's classpath.

4.  **Build:**
    *   Compile the Java source files. You can use an IDE like IntelliJ IDEA or a build tool like Maven or Gradle.

## Usage

1.  Run the `main` method in `src/app/Main.java`.
2.  The application will start, and you will be prompted to log in.
3.  Depending on your user role (Admin or Partner), you will have access to different features.
