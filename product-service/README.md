# Product Microservice

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
                .info(new Info().title("Product Service")
                        .description("REST API for product service")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Product service Wiki")
                        .url("https://github.com/yhdc/spring_cloud_msa/product_service"));
    }
}
```

## Server-to-Server Communication - Rest Client

For the communication between services, I have implemented RestClient.
Here I have set up a single custom rest client so that multiple services can implement.
There are three rest client services. First one is for managing inventory for each product 
and the other two for managing images and videos for each of products.

```java
// RestClient configuration for the Inventory service
@Configuration
public class RestClientConfig {
    @Bean
    public RestClient customRestClient(RestClient.Builder restClientBuilder) {

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);

        return restClientBuilder
                .requestFactory(requestFactory)
                .build();
    }
}
```
As an example, below is the Inventory Rest Client service that calls for stock check for a product.

```java
public class InventoryRestClientService {

    private static final String inventoryServerUrl = "lb://INVENTORY-SERVICE/inventory";
    private final RestClient restClient;
    
    /**
     * CHECK STOCK
     *
     * @param productId
     * @param skuCode
     * @param quantity
     * @implNote
     */
    public String isInStock(String productId,
                            String skuCode,
                            String quantity) {

        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("skuCode", skuCode);
        params.put("quantity", quantity);

        return restClient.get()
                .uri(inventoryServerUrl + "/stock-check", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
    }
}
```

> NOTE: gRpc protocol will be added as a comparison for the next major release version

## Testing - Testcontainers

> Testcontainers is a library that provides easy and lightweight APIs for bootstrapping local development
> and test dependencies with real services wrapped in Docker containers. Using Testcontainers,
> you can write tests that depend on the same services you use in production without mocks or
> in-memory services.

```java

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    String testUserId = UUID.randomUUID().toString();
    String testStoreId = UUID.randomUUID().toString();
    String testProductId;
    
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
        RestAssured.baseURI = "http://localhost:" + port + "/product";
    }

    @Test
    void shouldCreateProduct() {

        ProductCreateRecord productCreateRecord = new ProductCreateRecord(
                testUserId,
                testStoreId,
                "Test Product",
                "Testing product",
                "12000",
                "ACTIVE",
                "20",
                UUID.randomUUID().toString()
        );

        JsonPath jsonPath = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productCreateRecord)
                .when()
                .post("/create")
                .then()
                .statusCode(201)
                .body("productId", Matchers.notNullValue())
                .extract()
                .jsonPath();

        testProductId = jsonPath.getString("productId");
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
# For the actuator/info page
info.app.name=Product Service
info.app.description=Product Management Service
info.app.version=1.0.0
info.app.author=Daniel Choi
```

