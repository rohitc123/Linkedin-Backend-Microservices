# Professional Networking Platform – Microservices Backend

A scalable, containerized backend application inspired by professional networking platforms, built using Java, Spring Boot, and Microservices Architecture. Supports user management, posts, social connections, notifications, and media uploads.

# Project Overview

This project demonstrates the development of a distributed backend system using a Microservices Architecture.

The application is divided into independent services, where each service handles a specific business responsibility. Services communicate through REST APIs and asynchronous messaging using Apache Kafka.

An API Gateway acts as the entry point for client requests, while Eureka provides service discovery. Docker Compose is used to orchestrate the application infrastructure and services


# Key Features

**User Management**: User registration and authentication.

-**Post Management**: Create posts with media upload integration, retrieve, delete, like, and unlike posts.

**Connection Management**: Create and manage professional connections by sending, accepting, and rejecting connection requests.

**Notifications**: Asynchronous notification processing using Apache Kafka.

**Media Upload**: Upload images, PDFs, and videos using Cloudinary.

**JWT Authentication**: Token-based authentication with centralized validation at the API Gateway.

**Authorization**: Role-based access control and ownership validation.

**Service Discovery**: Dynamic service registration and discovery using Eureka.

**Containerization**: Dockerized infrastructure using Docker Compose.


# System Architecture

The application follows a Microservices Architecture, in which each service is responsible for a specific business capability.

**Architecture Diagram** 

