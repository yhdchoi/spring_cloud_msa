# Inventory Microservice

## API Documentation - Swagger

The Swagger aka. OpenAPI has become a standard for API documentation which is crucial for managing APIs efficiently.
It simplifies API development by documenting, designing and consuming RESTful services.

```properties
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
```

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Inventory Service")
                        .description("REST API Docs for inventory service")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Inventory service Wiki")
                        .url("https://github.com/yhdc/spring_cloud_msa/inventory_service"));
    }
}
```

## Testing - Testcontainers

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

## Monitoring - Actuator

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

