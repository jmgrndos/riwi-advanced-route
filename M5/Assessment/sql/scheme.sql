-- Create database
CREATE DATABASE libronova_assessment_db;

-- Connect to database
\c libronova_assessment_db;

-- Users table
CREATE TABLE users
(
    user_id  SERIAL PRIMARY KEY,
    email    TEXT UNIQUE NOT NULL,
    password TEXT        NOT NULL
);

-- Admins table
CREATE TABLE admins
(
    user_id INT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

-- Partners table
CREATE TABLE partners
(
    user_id          INT PRIMARY KEY,
    name             TEXT        NOT NULL,
    is_active        BOOLEAN     NOT NULL DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

-- Books table
CREATE TABLE books
(
    book_id        SERIAL PRIMARY KEY,
    title          TEXT NOT NULL,
    isbn           TEXT UNIQUE,
    stock          INT  NOT NULL DEFAULT 0
);

-- Loans table
CREATE TABLE loans
(
    loan_id     SERIAL PRIMARY KEY,
    book_id     INT  NOT NULL,
    user_id  INT  NOT NULL,
    loan_date   DATE NOT NULL DEFAULT CURRENT_DATE,
    due_date    DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES books (book_id),
    FOREIGN KEY (user_id) REFERENCES partners (user_id)
);

-- Create an admin user
INSERT INTO users (email, password) VALUES ('admin@example.com', 'password123');
INSERT INTO admins (user_id) VALUES (lastval());
