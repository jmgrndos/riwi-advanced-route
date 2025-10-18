# GDR Room Booking System

## Overview
This is a Java-based room booking management system for the GDR (Generic Data Room) project. It allows users to create, consult, and manage room bookings through a simple menu-driven interface. The application uses JDBC to connect to a MySQL database and follows a layered architecture for maintainability and scalability.

## Features
- Create and manage rooms
- Book rooms for specific dates and times
- Consult and update booking status
- View all rooms and bookings
- Input validation and error handling

## Project Structure
```
.
├── src/                # Source root foolder
│   ├── app             # Application entry point
│   ├── config          # Database connection factory
│   ├── controller      # Controllers for business logic coordination
│   ├── dao             # Data access objects for database operations
│   ├── domain          # Domain models (Room, Booking)
│   ├── exceptions      # Custom exception classes
│   ├── service         # Service layer for business rules
│   ├── util            # Utility classes (validation, exception mapping)
│   └── view            # User interface (menu and dialogs)
├── sql/ schema.sql     # Database schema
└── lib/                # External libraries (MySQL JDBC driver)
```

## Setup Instructions
1. **Clone the repository**
   ```bash
   git clone https://github.com/java-lovelace/jose-granados-gdr.git
   cd jose-granados-gdr
   ```
2. **Install MySQL and create the database**
   - Run the SQL script in `sql/schema.sql` to set up the database schema.
   - Update the database credentials in `src/config/ConnectionFactory.java` if needed.
3. **Add MySQL JDBC Driver**
   - Ensure `lib/mysql-connector-j-9.4.0.jar` is included in your classpath.
4. **Build and run the project**
   - Use your IDE or run from the command line:
   ```bash
   javac -cp "lib/mysql-connector-j-9.4.0.jar" src/app/Main.java
   java -cp "lib/mysql-connector-j-9.4.0.jar:src" app.Main
   ```

## Usage
- On startup, the main menu will appear.
- Navigate through room and booking menus to manage rooms and bookings.
- Follow on-screen prompts for input.