# Spring Cloud with Event-Driven Microservice Architecture

![yes](https://img.shields.io/badge/Maintained%3F-yes-green.svg)
![commit](https://img.shields.io/github/commits-since/yhdchoi/fiorano/1.0.svg)

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

> For this project, I am demonstrating a [Microservice Architecture](https://yhdchoi.notion.site/Microservice-Architecture-1d40b6ddbce580cdb8cfe3bcc877912b?pvs=4)
> with an [Event-Driven Architecture](https://yhdchoi.notion.site/Event-Driven-Architecture-1d40b6ddbce580f1a39fe756daf040d2?pvs=4). 
> MSA and EDA has become an industry standard due to its collection of loosely coupled services that operate together which can be modified, scaled, tested and deployed.
> With Event-Driven services, you can allow data transmission in real-time through asynchronous events.

## Getting Started

To run the project you will need Docker. You need to run the docker compose file within the Config Server first when
starting the project for the first time since the docker-compose.yml contains all the necessary containers to run the
application.

> ❗️IMPORTANT: This project is set up so that you can run on local environment as a demonstrations ONLY.

## 1. Config Server

Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system. With
the Config Server you have a central place to manage external properties for applications across all services.
<br/>

```properties
spring.application.name=config-server
server.port=8888
spring.profiles.active=native
```

The configurations can be pulled from Backend File System or Git repo. For this project I have located the congiuration
files under config server's resources directory.
<br/>

<img src="./readme/image/config-file-list.png" width="200" height="200"/>

## 2. Circuit Breaker - Resilience4j

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

For example, if the Store service fails due a sudden surge in traffic, the circuit breaker will cut off to the service.
After a
certain amount of time breaker will let part of the traffic in to check if the service operates as normal.
If it checks out to be ok, then breaker will let the service back to the original state.

<img src="./readme/image/circuitbreaker_resilience4j.png" width="600" height="400" />

#### Configurations:

```properties
resilience4j.circuitbreaker.configs.default.register-health-indicator=true
resilience4j.circuitbreaker.configs.default.sliding-window-type=COUNT_BASED
resilience4j.circuitbreaker.configs.default.sliding-window-size=10
resilience4j.circuitbreaker.configs.default.failure-rate-threshold=50
resilience4j.circuitbreaker.configs.default.wait-duration-in-open-state.seconds=3
resilience4j.circuitbreaker.configs.default.automatic-transition-from-open-to-half-open-enabled=true
resilience4j.circuitbreaker.configs.default.minimum-number-of-calls=5
resilience4j.timelimiter.configs.default.timeout-duration.seconds=3
resilience4j.retry.configs.default.max-attempts=3
resilience4j.retry.configs.default.wait-duration.seconds=2
```

```java
 @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            // Account Route
            .route("account_route", accoutnRoute -> accoutnRoute
                    .path("/account/**")
                    .filters(f -> f
                            .circuitBreaker(breaker -> breaker
                                    .setName("account_breaker")
                                    .setFallbackUri("forward:/fallback")
                            )
                            .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                    )
                    .uri("http://localhost:8081")

            )
            // skip //
            .build();

}
```

## 3. Gateway Reactive

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

## 4. Netflix Eureka Service Registry

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

## 5. Swagger

The Swagger aka. OpenAPI has become a standard for API documentation which is crucial for managing APIs efficiently.
It simplifies API development by documenting, designing and consuming RESTful services.

```properties
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.urls[0].name=Account Server
springdoc.swagger-ui.urls[0].url=/aggregate/account-server/v3/api-docs
springdoc.swagger-ui.urls[1].name=Store Server
springdoc.swagger-ui.urls[1].url=/aggregate/store-server/v3/api-docs
```

```java
  @Bean
public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            // SKIP //
            .route("account_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                    .path("/aggregate/account-service/v3/api-docs")
                    .filters(f -> f
                            .circuitBreaker(breaker -> breaker
                                    .setName("account_swagger_breaker")
                                    .setFallbackUri("forward:/fallback")
                            )
                    )
                    .uri("http://localhost:8081")
            )
            // SKIP//
            .build();
}
```

## 6. Security - Keycloak

When it comes to hacking prevention, Keycloak offers a more comprehensive set of features than Spring Security. Keycloak
provides advanced features like multi-factor authentication (MFA), brute force protection, and CAPTCHA support. MFA adds
an extra layer of security by requiring users to provide additional authentication factors besides their password, such
as a fingerprint or a security token. Brute force protection prevents attackers from repeatedly guessing passwords by
locking out the user’s account after a certain number of failed login attempts. CAPTCHA support helps prevent automated
bot attacks by requiring users to solve a puzzle or answer a question before logging in.

#### A. Up and running the Keycloak

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

#### B. Setting up the keycloak client

Now you can login to the keycloak dashboard and create a client for this project.
<br/>
After creating the credential, you can access the OpenID endpoints through the link provided under 'Realm settings'.

#### C. API Gateway security configuration

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

## 7. Actuator

Spring Boot Actuator is a sub-project of Spring Boot that provides a set of built-in production-ready features to help
you monitor and manage your application.
Actuator includes several endpoints that allow you to interact with the application, gather metrics, check the health,
and perform various management tasks.

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

## 8. Rest Client

For the synchronous communication between Video-Catalog service and Video-Stream service, I have implemented Rest
Template.

```java
@Configuration
public class RestClientConfig {
    @Bean
    public InventoryRestClient inventoryRestClient() {
        final String inventoryUrl = "http://localhost:8085/inventory";

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);
        requestFactory.setReadTimeout(30000);

        RestClient inventoryClient = RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(inventoryUrl).build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(inventoryClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryRestClient.class);
    }
}
```

## 9. Kafka

As a part of Event-Driven Architecture, Kafaka has been implemented for notification service 
since it can handle massive ammount of data in real-time through event streaming and stream processing.

<img src="./readme/image/kafka_diagram.png" width="500" height="200" />

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.template.default-topic=order-process
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.producer.properties.schema.registry.url=http://127.0.0.1:8201
```


```java
   @Override
    @Transactional
    public ResponseEntity<?> processOrder(OrderRequestRecord orderRequestRecord) {
    // SKIP //

    // Save order and process
    final Order order = orderRepository.save(createOrder(orderRequestRecord, totalPrice));

    // Send message to Kafka topic
    OrderProcessEvent orderProcessEvent = new OrderProcessEvent(
            order.getId().toString(),
            orderRequestRecord.userDetail().username(),
            orderRequestRecord.userDetail().firsName(),
            orderRequestRecord.userDetail().lastName(),
            orderRequestRecord.userDetail().userEmail()
    );
    log.info("Order process event sent to kafka topic: [ {} ]", order.getId());

    // Kafka
    kafkaTemplate.send("order-process", orderProcessEvent);
    
   // SKIP // 
    }
```

## 10. Testcontainers

Testcontainers is a library that provides easy and lightweight APIs for bootstrapping local development 
and test dependencies with real services wrapped in Docker containers. Using Testcontainers, 
you can write tests that depend on the same services you use in production without mocks or 
in-memory services.

```java

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreServiceApplicationTests {

    @LocalServerPort
    private int port;

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mongoDBContainer.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldCreateStore() {
        final String testSellerId = UUID.randomUUID().toString();
        final String testName = "Test Store";
        final String testDescription = "Test Description";
        final String testStatus = StoreStatus.ACTIVE.selection();

        StoreCreateRecord store = new StoreCreateRecord(
                testSellerId,
                testName,
                testDescription,
                testStatus
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(store)
                .when()
                .post("/store/create")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("sellerId", Matchers.equalTo(testSellerId))
                .body("name", Matchers.equalTo(testName))
                .body("description", Matchers.equalTo(testDescription))
                .body("status", Matchers.equalTo(testStatus));
    }
}
```


## 11. Docker & Kubernetes

In modern application 

```yaml
  ### KAFKA ORCHESTRATION
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    container_name: zookeeper
    hostname: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  ### KAFKA
  kafka:
    image: confluentinc/cp-kafka:latest
    container_name: kafka
    ports:
      - "9092:9092"
      - "29092:29092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092,PLAINTEXT_HOST://kafka:29092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    depends_on:
      - kafka
    ports:
      - "8200:8080"
    environment:
      KAFKA_CLUSTERS_NAME: local
      KAFKA_CLUSTERS_BOOTSTRAPSERVERS: kafka:29092
      KAFKA_CLUSTERS_ZOOKEEPER: zookeeper:2181
      KAFKA_CLUSTER_SCHEMAREGISTRY: http://schema-registry:8081
      DYNAMIC_CONFIG_ENABLED: 'true'

  ### SCHEMA
  schema-registry:
    image: confluentinc/cp-schema-registry:latest
    hostname: schema-registry
    container_name: schema-registry
    depends_on:
      - kafka
    ports:
      - "8201:8081"
    environment:
      SCHEMA_REGISTRY_HOST_NAME: schema-registry
      SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS: 'kafka:29092'
      SCHEMA_REGISTRY_LISTENERS: http://schema-registry:8081

```