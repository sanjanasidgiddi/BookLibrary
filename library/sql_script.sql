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

-- Admin User
INSERT INTO USERS (username, password, email, first_name, last_name, phone_number, dob, role) 
VALUES 
('johnsmith', 'securePassword', 'john.smith@gmail.com', 'John', 'Smith', '1112223333', '1985-01-15', 'ADMIN');

-- Regular Users
INSERT INTO USERS (username, password, email, first_name, last_name, phone_number, dob, role) 
VALUES
('emilyjohnson', 'securePassword', 'emily.johnson@gmail.com', 'Emily', 'Johnson', '2223334444', '1990-03-22', 'USER'),
('michaelbrown', 'securePassword', 'michael.brown@gmail.com', 'Michael', 'Brown', '3334445555', '1987-06-12', 'USER'),
('sarahdavis', 'securePassword', 'sarah.davis@gmail.com', 'Sarah', 'Davis', '4445556666', '1992-11-05', 'USER'),
('davidmiller', 'securePassword', 'david.miller@gmail.com', 'David', 'Miller', '5556667777', '1983-08-19', 'USER'),
('oliviagarcia', 'securePassword', 'olivia.garcia@gmail.com', 'Olivia', 'Garcia', '6667778888', '1995-02-10', 'USER'),
('jamesmartinez', 'securePassword', 'james.martinez@gmail.com', 'James', 'Martinez', '7778889999', '1989-12-25', 'USER'),
('isabellarodriguez', 'securePassword', 'isabella.rodriguez@gmail.com', 'Isabella', 'Rodriguez', '8889990000', '1993-07-17', 'USER'),
('ethanwilliams', 'securePassword', 'ethan.williams@gmail.com', 'Ethan', 'Williams', '9990001111', '1991-04-30', 'USER'),
('miasmith', 'securePassword', 'mia.smith@gmail.com', 'Mia', 'Smith', '0001112222', '1994-09-15', 'USER');


SELECT * FROM BOOKS

INSERT INTO BOOKS (book_name, author, book_genre, book_age_limit, image) VALUES
('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', 'Fantasy', 8, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1474169725i/15881.jpg'),
('To Kill a Mockingbird', 'Harper Lee', 'Fiction', 12, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1553383690l/2657._SY475_.jpg'),
('The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', 14, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1490528560l/4671.jpg'),
('1984', 'George Orwell', 'Dystopian', 16, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1348990566l/5470.jpg'),
('The Catcher in the Rye', 'J.D. Salinger', 'Classic', 14, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1398034300l/5107.jpg'),
('Pride and Prejudice', 'Jane Austen', 'Romance', 12, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1320399351l/1885.jpg'),
('The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 10, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1546071216l/5907.jpg'),
('Fahrenheit 451', 'Ray Bradbury', 'Science Fiction', 14, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1383718290l/13079982.jpg'),
('Jane Eyre', 'Charlotte Bronte', 'Classic', 14, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1327867269l/10210.jpg'),
('Animal Farm', 'George Orwell', 'Dystopian', 12, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1424037542l/7613.jpg'),
('The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy', 14, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1411114164l/33.jpg'),
('The Alchemist', 'Paulo Coelho', 'Philosophy', 10, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1483412266l/865.jpg'),
('The Chronicles of Narnia', 'C.S. Lewis', 'Fantasy', 8, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1546092655l/11127.jpg'),
('The Kite Runner', 'Khaled Hosseini', 'Drama', 16, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1484565687l/77203.jpg'),
('Moby Dick', 'Herman Melville', 'Adventure', 14, 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1327940656l/153747.jpg');

--USER JSON
[
    {
        "username": "johnsmith",
        "password": "securePassword",
        "email": "john.smith@gmail.com",
        "firstName": "John",
        "lastName": "Smith",
        "phoneNumber": "1112223333",
        "dob": "1985-01-15",
        "role": "ADMIN"
    },
    {
        "username": "emilyjohnson",
        "password": "securePassword",
        "email": "emily.johnson@gmail.com",
        "firstName": "Emily",
        "lastName": "Johnson",
        "phoneNumber": "2223334444",
        "dob": "1990-03-22",
        "role": "USER"
    },
    {
        "username": "michaelbrown",
        "password": "securePassword",
        "email": "michael.brown@gmail.com",
        "firstName": "Michael",
        "lastName": "Brown",
        "phoneNumber": "3334445555",
        "dob": "1987-06-12",
        "role": "USER"
    },
    {
        "username": "sarahdavis",
        "password": "securePassword",
        "email": "sarah.davis@gmail.com",
        "firstName": "Sarah",
        "lastName": "Davis",
        "phoneNumber": "4445556666",
        "dob": "1992-11-05",
        "role": "USER"
    },
    {
        "username": "davidmiller",
        "password": "securePassword",
        "email": "david.miller@gmail.com",
        "firstName": "David",
        "lastName": "Miller",
        "phoneNumber": "5556667777",
        "dob": "1983-08-19",
        "role": "USER"
    },
    {
        "username": "oliviagarcia",
        "password": "securePassword",
        "email": "olivia.garcia@gmail.com",
        "firstName": "Olivia",
        "lastName": "Garcia",
        "phoneNumber": "6667778888",
        "dob": "1995-02-10",
        "role": "USER"
    },
    {
        "username": "jamesmartinez",
        "password": "securePassword",
        "email": "james.martinez@gmail.com",
        "firstName": "James",
        "lastName": "Martinez",
        "phoneNumber": "7778889999",
        "dob": "1989-12-25",
        "role": "USER"
    },
    {
        "username": "isabellarodriguez",
        "password": "securePassword",
        "email": "isabella.rodriguez@gmail.com",
        "firstName": "Isabella",
        "lastName": "Rodriguez",
        "phoneNumber": "8889990000",
        "dob": "1993-07-17",
        "role": "USER"
    },
    {
        "username": "ethanwilliams",
        "password": "securePassword",
        "email": "ethan.williams@gmail.com",
        "firstName": "Ethan",
        "lastName": "Williams",
        "phoneNumber": "9990001111",
        "dob": "1991-04-30",
        "role": "USER"
    },
    {
        "username": "miasmith",
        "password": "securePassword",
        "email": "mia.smith@gmail.com",
        "firstName": "Mia",
        "lastName": "Smith",
        "phoneNumber": "0001112222",
        "dob": "1994-09-15",
        "role": "USER"
    }
]



--BOOKS JSON

[
    {
        "bookName": "Harry Potter and the Philosopher's Stone",
        "author": "J.K. Rowling",
        "bookGenre": "Fantasy",
        "bookAgeLimit": 8,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1474169725i/15881.jpg"
    },
    {
        "bookName": "To Kill a Mockingbird",
        "author": "Harper Lee",
        "bookGenre": "Fiction",
        "bookAgeLimit": 12,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1553383690l/2657._SY475_.jpg"
    },
    {
        "bookName": "The Great Gatsby",
        "author": "F. Scott Fitzgerald",
        "bookGenre": "Classic",
        "bookAgeLimit": 14,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1490528560l/4671.jpg"
    },
    {
        "bookName": "1984",
        "author": "George Orwell",
        "bookGenre": "Dystopian",
        "bookAgeLimit": 16,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1348990566l/5470.jpg"
    },
    {
        "bookName": "The Catcher in the Rye",
        "author": "J.D. Salinger",
        "bookGenre": "Classic",
        "bookAgeLimit": 14,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1398034300l/5107.jpg"
    },
    {
        "bookName": "Pride and Prejudice",
        "author": "Jane Austen",
        "bookGenre": "Romance",
        "bookAgeLimit": 12,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1320399351l/1885.jpg"
    },
    {
        "bookName": "The Hobbit",
        "author": "J.R.R. Tolkien",
        "bookGenre": "Fantasy",
        "bookAgeLimit": 10,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1546071216l/5907.jpg"
    },
    {
        "bookName": "Fahrenheit 451",
        "author": "Ray Bradbury",
        "bookGenre": "Science Fiction",
        "bookAgeLimit": 14,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1383718290l/13079982.jpg"
    },
    {
        "bookName": "Jane Eyre",
        "author": "Charlotte Bronte",
        "bookGenre": "Classic",
        "bookAgeLimit": 14,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1327867269l/10210.jpg"
    },
    {
        "bookName": "Animal Farm",
        "author": "George Orwell",
        "bookGenre": "Dystopian",
        "bookAgeLimit": 12,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1424037542l/7613.jpg"
    },
    {
        "bookName": "The Lord of the Rings",
        "author": "J.R.R. Tolkien",
        "bookGenre": "Fantasy",
        "bookAgeLimit": 14,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1411114164l/33.jpg"
    },
    {
        "bookName": "The Alchemist",
        "author": "Paulo Coelho",
        "bookGenre": "Philosophy",
        "bookAgeLimit": 10,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1483412266l/865.jpg"
    },
    {
        "bookName": "The Chronicles of Narnia",
        "author": "C.S. Lewis",
        "bookGenre": "Fantasy",
        "bookAgeLimit": 8,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1546092655l/11127.jpg"
    },
    {
        "bookName": "The Kite Runner",
        "author": "Khaled Hosseini",
        "bookGenre": "Drama",
        "bookAgeLimit": 16,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1484565687l/77203.jpg"
    },
    {
        "bookName": "Moby Dick",
        "author": "Herman Melville",
        "bookGenre": "Adventure",
        "bookAgeLimit": 14,
        "image": "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1327940656l/153747.jpg"
    }
]
