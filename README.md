# Spring Cloud 'Event-Driven' Microservice

# Introduction
![image](./readme/image/architecture-diagram.png)



# Spring Cloud

## Config Server
```java
public
```

## Circuit Breaker - Resilience4j

## Gateway Reactive
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

## Netflix Eureka Service Registry

## Security

# Spring Boot Actuator
## Configuration
```text
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


# Deployment





