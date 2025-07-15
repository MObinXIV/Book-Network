# Book Social Network web application

## Overview
Book Social Network is 
a comprehensive 
platform that allows users to organize their 
personal book collections and connect with fellow readers. 
Key features include user 
signup with secure email verification, 
book management (create, update, share, archive), 
borrowing and returning books with availability checks, 
and return approval workflows. The application employs JWT-based authentication 
for security and follows REST API design principles. 
The backend is powered by Spring Boot 3 and Spring Security 6.


---

## Features

- User Registration: Users can register for a new account.
- Email Validation: Accounts are activated using secure email validation codes.
- User Authentication: Existing users can log in to their accounts securely.
- Book Management: Users can create, update, share, and archive their books.
- Book Borrowing: Implements necessary checks to determine if a book is borrowable.
- Book Returning: Users can return borrowed books.
- Book Return Approval: Functionality to approve book returns.

## Technologies

- Spring Boot 3
- Spring Security 6
- JWT Token Authentication
- Spring Data JPA
- JSR-303 and Spring Validation
- OpenAPI and Swagger UI Documentation
- Docker