# CRUD Spring Boot Application

A RESTful CRUD application built using **Spring Boot**, **Spring Data JPA**, and **MySQL**. This project demonstrates complete CRUD operations, including **Create**, **Read**, **Update**, **Hard Delete** and **Soft Delete**, with APIs tested using **Postman**.

## 🚀 Features

- ➕ Create a new student
- 📄 Retrieve all students
- 🔍 Retrieve a student by ID
- ✏️ Update student details
- ❌ Hard delete a student
- ♻️ Soft delete a student
- 📦 DTO-based request and response handling
- ⚠️ Custom REST API exception handling
- 🔐 Global exception handling and Input validation
- 💾 MySQL database integration
- 🧪 API testing using Postman

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- IntelliJ IDEA
- Postman
- DBeaver

## 📂 Project Structure

```
src
├── main
│   ├── java
│   │   ├── controller
│   │   ├── dto
│   │   ├── entity
│   │   ├── exception
│   │   ├── repository
│   │   ├── service
│   │   └── CrudSpringBootDemoApplication.java
│   └── resources
│       └── application.properties
└── test
```

## 📌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/students` | Create a new student |
| GET | `/students` | Get all students |
| GET | `/students/id` | Get student by ID |
| PUT | `/students/id` | Update student |
| DELETE | `/students/id` | Hard delete student |
| PATCH | `/students/delete-soft/id` | Soft delete student |

> Replace the endpoint names if they are different in your project.

## ⚙️ Getting Started

### Clone the repository

```bash
git clone https://github.com/singhabhi206/crudeSpringBootDemo.git
```

### Navigate to the project

```bash
cd crudeSpringBootDemo
```

### Configure MySQL

Update the `application.properties` file with your MySQL credentials.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Run the application

```bash
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

## 🧪 API Testing

All REST APIs were tested using **Postman**.

## 👨‍💻 Author

**Abhishek Kumar Singh**

- GitHub: https://github.com/singhabhi206
- LinkedIn: https://www.linkedin.com/in/abhishek-kumar-singh-369335296/

---

⭐ If you found this project useful, consider giving it a star!
