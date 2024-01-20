# Read Me First

## Project Overview

This project is developed using Java and Spring Boot, organized into three main sub-packages: investor, product, and withdrawal. Each package serves a specific purpose in managing investors, products, and withdrawal operations.

### Package Details

- **Investor Package:**
    - Handles all aspects related to investors.
    - Provides functionalities necessary for investor creation.

- **Product Package:**
    - Manages products.
    - Offers features required for product creation.

- **Withdrawal Package:**
    - Dedicated to withdrawal operations.
    - Sends alerts to investors regarding the withdrawal process.

### Controllers

Each package includes controllers responsible for managing URL requests.

### Dependencies

In order to invest (create a product), an investor must first be created, and the investor's ID must be known. Similarly, to perform a withdrawal, knowledge of the product's ID is required.

# Getting Started

## Curl Commands

**NB:** Items enclosed with "{}" indicate that a value is required.

### Create an Investor

```bash
curl -X POST -H "Content-Type: application/json" -d '{"name": "{NameOfInvestor}", "address": "{AddressOfInvestor}",
"contact": "{ContactOfInvestor}", "age": {age} }' http://localhost:8080/investors
```

### Create an Investment (Product)

```bash
curl -X POST -H "Content-Type: application/json" -d '{"name": "{New Product}", "type": "{Some Type}",
"balance": {e.g., 1000.0}, "investorId": {investorID}}' http://localhost:8080/products
```

### Perform a Withdrawal

```bash
curl -X POST -H "Content-Type: application/json" -d '{"productId": {productID}, "withdrawalAmount": {amount: e.g., 500.0}, "bankName":
"{bankName}", "accountNumber": "{accountNumber}"}' http://localhost:8080/withdrawals
```

# Reference Documentation

For further reference, consider the following sections:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/#build-image)
- [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#web)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

# Guides

Explore the following guides to utilize specific features concretely:

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)