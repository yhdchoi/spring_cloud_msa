# Spring Cloud 'Event-Driven' Microservice

# Introduction
![image](./readme/image/architecture-diagram.png)



# Spring Cloud

## 1. Config Server
Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system. With the Config Server you have a central place to manage external properties for applications across all environments.

```properties
spring.application.name=config-server
server.port=8888
spring.profiles.active=native
```
The configurations can be pulled from Backend File System or Git repo. For this project I have located the congiuration files under config server's resources directory.
<br/>
<img src="./readme/image/config-file-list.png" width="200" height="200"/>

## 2. Circuit Breaker - Resilience4j




## 3. Gateway Reactive
I have used the Fluent Java Routes APi since I am using Reactive for my gateway. In this way I can easily manage and update routes.
### Fluent Java Routes API
```java
@Bean
public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            .route(userRoute -> userRoute.path("/usr/**")
                    .filters(f -> f
                            .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                    )
                    .uri("http://localhost:8081")
    
            )
            .route(storeRoute -> storeRoute.path("/str/**")
                    .filters(f -> f
                            .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                    )
                    .uri("http://localhost:8082")
    
            )
            
            << ... skip ... >>
            
        .build();

```

## 4. Netflix Eureka Service Registry

## 5. Security

# Spring Boot Actuator
Actuator endpoints let you monitor and interact with your application.

Configuration
```properties
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
management.info.env.enabled=true

# For the actuator/info page
info.app.name=Account Server
info.app.description=Account(User) Management Service
info.app.version=1.0.0
info.app.author=Daniel Choi
```

# Microservices
## Load Balancer
## Rest Template

# Databases
## MariaDB

## MongoDB


## PostgreSQL



# Deployment
## Docker



## Kubernetes




