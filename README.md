# Library Management System

## Project Description

This is a Library Management System API built using Spring Boot. The system allows librarians to manage books, patrons, and borrowing records. It provides RESTful endpoints for CRUD operations on books and patrons and tracks borrowing activities.

## Features

- **Book Management**: Add, update, retrieve, and delete books.
- **Patron Management**: Add, update, retrieve, and delete patrons.
- **Borrowing Records**: Track book borrowings and returns.

## Entities

- **Book**: Represents a book with attributes like ID, title, author, publication year, and ISBN.
- **Patron**: Represents a library patron with attributes like ID, name, mobile, email, and address.
- **BorrowingRecord**: Represents the borrowing relationship between a book and a patron, including borrow and return dates.

## API Endpoints

### Book Management

- **GET /api/books**: Retrieve a list of all books.
- **GET /api/books/{id}**: Retrieve details of a specific book by ID.
- **POST /api/books**: Add a new book to the library.
- **PUT /api/books/{id}**: Update an existing book's information.
- **DELETE /api/books/{id}**: Remove a book from the library.

### Patron Management

- **GET /api/patrons**: Retrieve a list of all patrons.
- **GET /api/patrons/{id}**: Retrieve details of a specific patron by ID.
- **POST /api/patrons**: Add a new patron to the system.
- **PUT /api/patrons/{id}**: Update an existing patron's information.
- **DELETE /api/patrons/{id}**: Remove a patron from the system.

### Borrowing

- **POST /api/borrow/{bookId}/patron/{patronId}**: Allow a patron to borrow a book.
- **PUT /api/return/{bookId}/patron/{patronId}**: Record the return of a borrowed book by a patron.

## Getting Started

### Prerequisites

- Java 11 or later
- Maven
- MySQL

### Setup

1. **Clone the Repository:**

    ```bash
    git clone https://github.com/MayarHamed/LibraryApplication
    cd LibraryApplication
    ```

2. **Configure Database (if using MySQL):**
   
   Update the `application.properties` file with your MySQL database configuration.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/library_db
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    

3. **Build and Run the Application:**

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. **Access the Application:**

    The application will be available at `http://localhost:8080`.

## Testing

To run unit tests, use the following Maven command:

```bash
mvn test
