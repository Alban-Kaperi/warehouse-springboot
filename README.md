## Inventory API

## Overview
The **Inventory API** is a sample project designed to demonstrate and experiment with Spring Boot development. The project may contain experimental configurations and is structured to serve as a sandbox for exploring Spring Boot, containerization, and deployment in Kubernetes environments.

> **Note:** This repository is not intended for production use and includes experimentation-related configurations. One such is the presence of an exposed security key in the `application.properties` file, which is deliberately left for demonstration purposes.

## Features
- Built with **Spring Boot** to showcase the rapid development of RESTful APIs.
- Basic API endpoints targeted at inventory management tasks.
- Integration and testing support for Kubernetes.

## Environment Setup
### Prerequisites
To work on this project, ensure that the following dependencies are installed on your system:
- **Java 17** or higher
- **Maven 3.8+**
- **Docker Desktop and Kubernetes enabled**


### Clone and Open the project
- Clone the repository:
``` bash
   git clone <repository_url>
```
- Navigate to the project directory:
``` bash
   cd inventory-api
```

and open it with e.g. IntelliJ

### How to Run

There are 2 scenarios:

- running it withing your ide e.g. IntelliJ:
  - you need to modify the application.properties use this connection `spring.datasource.url=jdbc:mysql://localhost:3306/inventory`

- deploying it with kubernetes which is illustrated below.

Access the API at `http://localhost:8080`or `http://localhost:8080/swagger-ui/index.html#/`

## Kubernetes

If you plan to test the application in Kubernetes, follow these additional steps:
1. you need to modify the application.properties use this connection `spring.datasource.url=jdbc:mysql://host.docker.internal:3306/inventory`

2. Create the database with docker-compose
```bash
    cd inventory-api
    docker-compose up -d
```
3. Build the Docker image of the application:
``` bash
   docker build -t inventory-api .
```
4. Deploy to Kubernetes:
``` bash
   kubectl apply -f .\infra\deployment.yml
```
5. Verify the deployment and expose the service as needed.

> In kubernetes I have used NodePort service to expose the app, it runs on port `30303`

Access the API at `http://localhost:30303`or `http://localhost:30303/swagger-ui/index.html#/`
