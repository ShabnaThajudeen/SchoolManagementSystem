This is a School Management Software that should match the high standards of the school that they aspire to build.
The software follow microservices architecture. 
It is cohesive, loosely coupled, agile, flexible, highly scalable, highly available, resilient and distributed Spring Boot application.

Design Patterns used: API Gateway, Service Discovery and Registry with client side load balancing, Resiliency Pattern - Circuit breaker, Domain Driven Design, Dependency Injection, Database per service.

Microservices:
apigateway: The reactive gateway for all the microservices.
eureka-server: The Spring Boot microservice for service registery and discovery using Java tool Netflix Eureka.
oauth-server: An implementation for own authorization server using OAuth2 protocol. A Spring Boot Microservice.
studentms: A Spring Boot microservice, to manage the students and to invoke student fee payment reactively.
feesms: A Spring Boot microservice to manage student fee details.

Technology Stack:
Back-end framework & Microservices - Java 17, Spring Boot 3, Spring MVC, Spring Security (OAuth2), Microservices, Netflix Eureka
API & Data Interchange - RESTful API, YML
Data Access & Persistence - Spring Data JPA, IBM DB2
DevOps, Containerization & Infrastructure - Docker, Docker Hub, Apache Tomcat
Build, Version Control & Operations - Maven, Git/GitHub, SLF4J
