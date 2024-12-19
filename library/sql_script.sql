CREATE TABLE IF NOT EXISTS "Library".users (
    username TEXT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    DOB DATE CHECK (DOB <= CURRENT_DATE)
);

CREATE TABLE IF NOT EXISTS "Library".books (
    book_id SERIAL PRIMARY KEY,
    book_name VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    book_genre VARCHAR(255) NOT NULL,
    book_age_limit INTEGER CHECK (book_age_limit >= 0) NOT NULL
);

CREATE TABLE IF NOT EXISTS "Library".booklogs (
    book_log_id SERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    book_id INTEGER NOT NULL,
    date_issued DATE NOT NULL,
    date_to_be_returned DATE NOT NULL,
    date_actually_returned DATE,
    FOREIGN KEY (username) REFERENCES "Library".users(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES "Library".books(book_id) ON DELETE CASCADE ON UPDATE CASCADE
);

SELECT * FROM USERS

INSERT INTO USERS (username, password, email, first_name, last_name, phone_number, dob, role) 
VALUES ('tomcat', 'securePassword', 'tomcat@gmail.com', 'Tom', 'Cat', '1234567898', '2024-09-01', 'ADMIN');

INSERT INTO USERS (username, password, email, first_name, last_name, phone_number, dob, role) 
VALUES ('ginagina', 'securePassword', 'ginagina@gmail.com', 'Gina', 'Gina', '9876543212', '1990-05-08', 'USER');

DELETE FROM USERS
WHERE USERNAME=''

{
    "username"="ginagina",
    "bookId"=1,
    "date_issued": 2024/12/01,
    "date_issued": 2024/12/19,
}
