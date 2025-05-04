# Account Microservice

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
                .info(new Info().title("Account Service API")
                        .description("REST API for account service")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Account service Wiki")
                        .url("https://github.com/yhdc/spring_cloud_msa/store_server"));
    }
}
```

## 3. Testing - Testcontainers

> Testcontainers is a library that provides easy and lightweight APIs for bootstrapping local development
> and test dependencies with real services wrapped in Docker containers. Using Testcontainers,
> you can write tests that depend on the same services you use in production without mocks or
> in-memory services.

```java

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceApplicationTests {

    String testUserId;

    @LocalServerPort
    private int port;

    @ServiceConnection
    static MariaDBContainer mariaDBContainer = new MariaDBContainer("mariadb:latest");

    @BeforeAll
    static void beforeAll() {
        mariaDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mariaDBContainer.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port + "/account";
    }

    @Test
    void shouldCreateAccount() {
        UserCreateRecord account = new UserCreateRecord("testuser",
                "password@1q2w3e",
                "Daniel",
                "Choi",
                "63 Young st",
                "yhdc@email.com",
                "010-2233-4234",
                "MANAGER");

        JsonPath jsonPath = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(account)
                .when()
                .post("/register")
                .then()
                .statusCode(201)
                .body("userId", Matchers.notNullValue())
                .extract()
                .jsonPath();

        testUserId = jsonPath.getString("userId");
    }

    // <<< SKIP >>>

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
# For the actuator "/info"
info.app.name=Account Server
info.app.description=Account(User) Management Service
info.app.version=1.0.0
info.app.author=Daniel Choi
```