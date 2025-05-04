# Video Stream Microservice

## API - Swagger

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
                .info(new Info().title("Video Stream Service API")
                        .description("REST API Docs for video stream service")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Video stream service Wiki")
                        .url("https://github.com/yhdc/spring_cloud_msa/video-stream-service"));
    }
}
```

## Server-to-Server Communication - Rest Client

For the communication between services, I have implemented RestClient.
This is a custom rest client which can be modified to fit for the multiple client services.

```java

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
Below example demonstrates a scenario where the client-side requests to stream the video, the video stream service requests
video catalog service for the video path to stream the correct video file.

```java
public class VideoCatalogRestClientService {

    private static final String videoCatalogServerUrl = "lb://VIDEO-CATALOG-SERVICE/video-catalog";

    private final RestClient restClient;


    /**
     * GET VIDEO PATH
     *
     * @param videoInfoId
     * @implNote For streaming video
     */
    public String loadVideoPath(String videoInfoId) {
        return restClient.get()
                .uri(videoCatalogServerUrl + "/find-path/{videoInfoId}", videoInfoId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
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
info.app.name=Video Stream Service
info.app.description=Video Stream Management Service
info.app.version=1.0.0
info.app.author=Daniel Choi
```

