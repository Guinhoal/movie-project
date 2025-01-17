# Cinema Management System

## Introduction
This project is a cinema management system developed in Java with MySQL as the database. The project includes functionalities such as listing sessions, creating rooms, movies, and sessions, purchasing tickets, checking room status, and verifying movie end times. The project is organized following the MVC pattern and uses Docker for easy setup and deployment.

## Project Structure
- `src` - Common Java package (contains `.java` files)
- `src/main` - Main Java package, containing `resources` and `java` sub-packages
- `src/main/resources` - Java resources package, contains `db.properties` for database connection
- `src/main/java` - Main Java package, contains the `Main.java` file and other sub-packages
- `src/main/java/cinema` - Contains classes related to Cinema, such as `Cinema`, `CinemaA`, `CinemaB`, `Sessao`, `Sala`, and `Filme`
- `src/main/java/dao` - Contains Data Access Object (DAO) classes for database operations
- `src/main/java/conexao` - Contains connection-related files like `ConnectionFactory.java`
- `src/main/java/exceptions` - Contains custom exception classes

## Features
- List Sessions
- Create Rooms, Movies, and Sessions
- Purchase Tickets
- Check Room Status
- Verify Movie End Times
- List Movies
- List Cinemas

## Technologies Used
- Java
- MySQL
- Docker

## How to Use
1. Start the Docker container with the command:
    ```sh
    docker-compose up -d
    ```

2. Access the MySQL container and log in as root:
    ```sh
    docker start cinema_db
    docker exec -it cinema_db mysql -u root -p
    ```

3. Execute the SQL script to create and populate the database:
    ```properties
    libs/cinema.sql
    ```

4. Run the project normally.

## Notes
- The project uses eager initialization for the singleton pattern.
- The code follows the MVC pattern for better organization.
- Docker is used to simplify the setup and deployment process.

## Contact
For any questions or issues, please feel free to reach out.