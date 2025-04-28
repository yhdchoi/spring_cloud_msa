# Account Microservice

## 1. API - Swagger

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

## 2. Monitoring - Actuator

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

## 3. Testing - Testcontainers

> Testcontainers is a library that provides easy and lightweight APIs for bootstrapping local development
> and test dependencies with real services wrapped in Docker containers. Using Testcontainers,
> you can write tests that depend on the same services you use in production without mocks or
> in-memory services.

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
