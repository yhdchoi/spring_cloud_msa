# Spring Cloud 'Event-Driven' Microservice

![yes](https://img.shields.io/badge/Maintained%3F-yes-green.svg)
![yes](https://img.shields.io/github/last-commit/yhdchoi/msa-cloud.svg)

![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![springsecurity](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![mariadb](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![postgresql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![mongodb](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)
![swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)
![intellij](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

## Architecture

![architecture](./readme/image/architecture-diagram.png)

[//]: # (<img src="./readme/image/architecture-diagram.png" width="800" height="800" />)

## Getting Started

To run the project you will need Docker. You need to run the docker compose file within the Config Server first when
starting the project for the first time since the docker-compose.yml contains all the necessary containers to run the
application.

> ❗️IMPORTANT: This project is set up so that you can run on local environment as a demonstrations ONLY.

## Config Server

Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system. With
the Config Server you have a central place to manage external properties for applications across all environments.
<br/>

```properties
spring.application.name=config-server
server.port=8888
spring.profiles.active=native
```

The configurations can be pulled from Backend File System or Git repo. For this project I have located the congiuration
files under config server's resources directory.
<br/>

[//]: # (<img src="./readme/image/config-file-list.png" width="200" height="200"/>)

## Circuit Breaker - Resilience4j

A distributed system, which comprises many services interacting to achieve business goals, is prone to failures in the
chain of service dependencies.
Suppose service A calls service B, which calls service C, but C does not respond. Service C may be down, or overloaded,
and take a long time to respond, causing errors that may cascade and cause the system to fail.

**According to Chris Richardson's *"Microservice Patterns"*:**

***A service client should invoke a remote service via a proxy that functions in a similar fashion to an electrical
circuit breaker.
When the number of consecutive failures crosses a threshold, the circuit breaker trips, and for the duration of a
timeout period all attempts to invoke the remote service will fail immediately.
After the timeout expires the circuit breaker allows a limited number of test requests to pass through.
If those requests succeed the circuit breaker resumes normal operation. Otherwise, if there is a failure the timeout
period begins again.***

If the Store service fails due a sudden surge in traffic, the circuit breaker will cut off to the service. After a
certain amount of time breaker will let part of the traffic in to check if the service operates as normal.
If it checks out to be ok, then breaker will let the service back to the original state.

[//]: # (<img src="./readme/image/architecture-diagram.png" width="500" height="500" />)

#### Configurations:

```properties

```

```java

```

## Gateway Reactive

I have used the Fluent Java Routes APi since I am using Reactive for my gateway. In this way I can easily manage and
update routes.

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
            .build();
}
```

You can find more endpoints for your routes here:
https://docs.spring.io/spring-boot/reference/actuator/endpoints.html

## Netflix Eureka Service Registry

The Eureka is a service registry that allows microservices to register themselves and discover other services. In
essence, it acts like a phone directory for your microservices, providing a mechanism for service-to-service discovery
and registration.

```properties
eureka.instance.hostname=localhost
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

I have the register and fetch settings to false since we don't want to register itself as a service.

```java

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

The @EnableEurekaServer annotation is added to run the server as a service registry server.

## Security - Keycloak

When it comes to hacking prevention, Keycloak offers a more comprehensive set of features than Spring Security. Keycloak
provides advanced features like multi-factor authentication (MFA), brute force protection, and CAPTCHA support. MFA adds
an extra layer of security by requiring users to provide additional authentication factors besides their password, such
as a fingerprint or a security token. Brute force protection prevents attackers from repeatedly guessing passwords by
locking out the user’s account after a certain number of failed login attempts. CAPTCHA support helps prevent automated
bot attacks by requiring users to solve a puzzle or answer a question before logging in.

#### 1. Up and running the Keycloak

For this project you need to deploy a keycloak container with its own database for metadata.
```shell
  docker compose up -d
```

```yaml
  keycloak:
    container_name: keycloak
    image: quay.io/keycloak/keycloak:26.2.0
    command: [ "start-dev", "--import-realm" ]
    environment:
      DB_VENDOR: MARIADB
      DB_ADDR: mariadb
      DB_DATABASE: keycloak
      DB_USER: root
      DB_PASSWORD: fiorano1q2w
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
    ports:
      - "8181:8080"
    volumes:
      - ./container/keycloak/realms/:/opt/keycloak/data/import
    depends_on:
      - mariadb-keycloak
```

#### 2. Setting up the keycloak client

Now you can login to the keycloak dashboard and create a client for this project.
<br/>
After creating the credential, you can access the OpenID endpoints through the link provided under 'Realm settings'.

#### 3. API Gateway security configuration

```java

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests.anyRequest()
                            .authenticated())
            .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(Customizer.withDefaults()))
            .build();
}
```

Above configuration will redirect all requests to the Keycloak authentication server.
The Keycloak authentication server then will handle issuing and validating JWT.

## Spring Boot Actuator

Actuator endpoints let you monitor and interact with your application.

#### Configuration

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

## Server Side Event - SSE

### Kafka

I have integrated Kafaka for ordering service since Kafka is designed for handling large volumes of data with minimal
latency.

### RabbitMQ

I have implemented RabbitMQ for Notification service since RabbitMQ is versatile and flexible, supporting
multi-protocols.

## Load Balancer

For communicating between each services

## Rest Template

I have implemented Rest Template to accomplish communication between the services.

```java

@Configuration
public class RestConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

## Testcontainers

## Springdoc-openapi (Documentation)


