# Welcome to AM parking Backend Repository 
This is a Java Spring Boot application for managing Am parking.


### Prerequisites
- Java 17 or higher
- MySQL


### 1. Clone the repository 
'''
git clone https://github.com/Abdiox/BackendAMParking.git
'''
### 2. Navigate to the project directory
'''
cd BackendAMParking
'''
### 3. Update the src/main/resources/application.properties file with your MySQL credentials
'''
spring.datasource.url=${JDBC_DATABASE_URL}
spring.datasource.username=${JDBC_USERNAME}
spring.datasource.password=${JDBC_PASSWORD}
'''

### The application should now be running at http://localhost:8080


### Swagger documentation
https://amparking-cvbzhddea9cjb0eh.northeurope-01.azurewebsites.net/swagger-ui/index.html

Build Status (Production branch)
![Backend Status](https://img.shields.io/website?url=https://amparking-cvbzhddea9cjb0eh.northeurope-01.azurewebsites.net)


Security
Security is inspired by this repository:
https://github.com/lmor-spring2024/security-start-backend
