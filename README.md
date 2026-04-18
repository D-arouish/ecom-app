# ecom-app

Full e-commerce platform built as a microservices-based system. This repository combines backend infrastructure, business services, an Angular frontend, Docker orchestration, Keycloak-based authentication assets, and Kubernetes deployment files.

## Architecture

The repository includes:

- `discovery-service` for Eureka service discovery
- `config-service` for centralized configuration
- `gateway-service` as the API entry point
- `customer-service`, `product-service`, and `vente-service` for business domains
- `notification-service` for asynchronous or supporting workflows
- `ecom-app-angular` for the frontend client
- `config-git-repo` and `realms` for supporting configuration and Keycloak setup
- `kubernetes` manifests for deployment experiments

## Stack

- Java 17 and Spring Boot
- Spring Cloud Config
- Eureka
- Spring Cloud Gateway
- Angular
- Docker Compose
- Keycloak
- Kubernetes

## What This Repository Demonstrates

- decomposition of an e-commerce platform into multiple services
- centralized configuration and service discovery
- gateway-based API exposure
- frontend and backend integration
- container-based local orchestration

## Run Locally

For the full stack, start with Docker Compose:

```bash
docker compose up --build
```

You can also work on services independently from their subdirectories.

## Notes

This repository acts as the umbrella project for the e-commerce platform. Some service-level READMEs inside the repository can be expanded further as the platform evolves.
