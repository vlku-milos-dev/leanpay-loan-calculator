# Leanpay Loan Calculator API

## Table of Contents
- [Task Requirement](#Task-Requirement)
- [Technologies](#Technologies)
- [Build Requirements](#Build-Requirements)
- [Documentation](#Documentation)

## Task Requirement
The goal is to create an application for a Loan Calculator.

### Create:
- REST API which returns installment plans based on the amount, annual interest percentage, and number of months
- Create a DB model and persist each request and calculation result to the database
- As an example for calculation, you can use the following links:
  - [Simple Loan Calculator](https://www.calculatorsoup.com/calculators/financial/loan-calculator-simple.php)
  - [Grooming explanation possible](https://www.calculatorsoup.com/calculators/financial/amortization-schedule-calculator.php?actions=update&ipv=1,000.00&inper=10&iper=12&irate=5)

### Our expectation is:
- Spring boot app - preferable
  - GitHub repository link
  - Integration & unit test 
  - UML and sequence diagram - nice to have

### Example of loan calculation:
#### Input params:
- amount: 1000e
- annual interest percent: 5%
- number of months: 10

#### Output parameters:
- month 1 = 102.31
- ...
- month 10 = 102.31

## Technologies
- Docker compose
- Java 17
- Gradle 8.11.1 (Wrapper already included in the project)
- Spring Boot 3.4.0


## Build Requirements
To build and run the application locally, you will need the following:
- Docker Compose
- Java 17 JDK
- Gradle 8.11.1

Steps to build and run the application:
1. Clone the repository
2. Navigate to the project directory
3. Run `docker compose up -d` to start the MySQL database
4. Run `./gradlew clean build` to build the application
5. Run `./gradlew bootRun` to run the application

## Documentation
[API Documentation (Swagger)](http://localhost:8080/leanpay/swagger-ui/index.html)


