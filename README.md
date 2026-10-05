# PayAxis — Employee Payroll Management System

PayAxis is a web-based **Employee Payroll Management System** designed to simplify employee management, attendance tracking, leave management, payroll processing, and salary administration.

The application follows a layered architecture using **Java Servlets, JSP, JDBC, DAO pattern, and MySQL**, with additional support for Excel-based data processing and PDF payslip generation.

## Overview

PayAxis provides separate access levels for **Administrators and Employees**, allowing organizations to manage employee information and payroll operations through a centralized web application.

The system is designed with a focus on:

* Role-based access control
* Maintainable backend architecture
* Secure password storage
* Database-driven payroll processing
* Automated salary calculations
* Employee self-service functionality
* Import and export of payroll data

## Key Features

### Authentication and Authorization

* Role-based login for Admin and Employee users
* SHA-256 password hashing
* Session-based authentication
* Role-specific access to application modules

### Employee Management

* Add new employees
* Update employee information
* View employee details
* Delete employee records
* Manage employee salary information

### Attendance Management

* Record daily employee attendance
* Track attendance history
* Maintain employee attendance records
* Use attendance information during payroll processing

### Leave Management

* Employees can submit leave applications
* Administrators can review and approve/reject requests
* Maintain leave history for employees

### Payroll Management

* Generate employee payroll based on salary and attendance data
* Automatic salary calculations
* Tax calculation using configurable tax slabs
* Maintain payroll history
* Generate salary records for employees

### Payslip Generation

* Generate employee payslips in PDF format
* Include salary, deductions, tax, attendance, and employee information
* Allow employees to access their salary records

### Excel Data Management

* Import employee/payroll data from Excel files
* Export payroll information to Excel
* Apache POI integration for spreadsheet processing

### User Interface

* Responsive web interface
* Bootstrap 5-based layout
* Separate dashboards for Admin and Employee users
* Structured navigation and reusable UI components

## Technology Stack

| Layer                 | Technology                                |
| --------------------- | ----------------------------------------- |
| Frontend              | JSP, HTML5, CSS3, JavaScript, Bootstrap 5 |
| Backend               | Java Servlets, Jakarta EE 10              |
| Database              | MySQL 8                                   |
| Database Connectivity | JDBC                                      |
| Architecture          | DAO / Layered Architecture                |
| Excel Processing      | Apache POI                                |
| PDF Generation        | Java PDF Library                          |
| Authentication        | SHA-256                                   |
| Web Server            | Apache Tomcat 10.1                        |
| IDE                   | Eclipse                                   |

## Application Architecture

The application follows a layered architecture to separate presentation, business, and database responsibilities.

```text
Client
  |
  v
JSP / HTML / CSS / JavaScript
  |
  v
Servlet Layer
  |
  v
Business Logic
  |
  v
DAO Layer
  |
  v
JDBC
  |
  v
MySQL Database
```

### Main Components

**Presentation Layer**

* JSP
* HTML
* CSS
* JavaScript
* Bootstrap

**Controller Layer**

* Java Servlets
* Request handling
* Session management
* Authentication and authorization

**Data Access Layer**

* DAO classes
* JDBC
* SQL queries
* Database connection management

**Database Layer**

* MySQL 8
* Employee records
* Attendance records
* Leave records
* Payroll records
* User credentials

## Project Structure

```text
PayAxis/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── payaxis/
│       │           ├── controller/
│       │           ├── dao/
│       │           ├── model/
│       │           ├── service/
│       │           └── util/
│       │
│       └── webapp/
│           ├── css/
│           ├── js/
│           ├── admin/
│           ├── employee/
│           └── WEB-INF/
│
├── database/
│   └── payaxis.sql
│
├── README.md
└── .gitignore
```

> The structure above can be adjusted according to the actual package and directory structure of the project.

## Database

PayAxis uses **MySQL 8** for persistent data storage.

The database manages entities such as:

* Users
* Employees
* Attendance
* Leave Applications
* Payroll
* Salary Records

A database script is provided in the `database` directory for initial setup.

## Prerequisites

Before running the application, make sure the following are installed:

* Java JDK 17 or compatible version
* Eclipse IDE
* Apache Tomcat 10.1
* MySQL 8
* MySQL Workbench
* Git

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/ritesh1552003-svg/PayAxis.git
```

### 2. Import the Project

Open Eclipse and import the project as a Dynamic Web Project or Maven project, depending on the project configuration.

### 3. Configure MySQL

Create the PayAxis database:

```sql
CREATE DATABASE payaxis;
```

Import the provided SQL script:

```text
database/payaxis.sql
```

### 4. Configure Database Connection

Update the database configuration with your MySQL credentials.

Example:

```java
String url = "jdbc:mysql://localhost:3306/payaxis";
String username = "root";
String password = "your_password";
```

Do not commit actual database passwords or other credentials to GitHub.

### 5. Configure Apache Tomcat

Add **Apache Tomcat 10.1** to Eclipse and configure the project to run on the Tomcat server.

### 6. Run the Application

Start the Tomcat server from Eclipse and open the application in a browser.

```text
http://localhost:8080/PayAxis/
```

## Security Considerations

The application includes several basic security measures:

* SHA-256 password hashing
* Session-based authentication
* Role-based authorization
* Prepared statements for database operations
* Separation of database access through DAO classes

For a production deployment, additional security measures such as **BCrypt/Argon2 password hashing, CSRF protection, input validation, HTTPS, secure session configuration, and centralized exception handling** should be implemented.

## Future Enhancements

Potential improvements include:

* Spring Boot migration
* REST API integration
* JWT-based authentication
* BCrypt/Argon2 password hashing
* Email notifications
* Automated payroll scheduling
* Advanced payroll analytics
* Dashboard charts and reports
* Cloud deployment
* Docker support
* Audit logging
* Multi-company payroll support

## Learning Outcomes

This project demonstrates practical experience with:

* Java web application development
* Servlet-based application architecture
* JSP
* JDBC and MySQL
* DAO pattern
* CRUD operations
* Authentication and authorization
* Session management
* File processing
* Excel automation using Apache POI
* PDF document generation
* Payroll calculation logic
* Relational database design

## Author

**Ritesh**

GitHub:
https://github.com/ritesh1552003-svg

## License

This project is intended for educational and portfolio purposes.
