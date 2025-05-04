# Video Catalog Microservice

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
                .info(new Info().title("Video Catalog Service")
                        .description("REST API Docs for video catalog service")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Video catalog service Wiki")
                        .url("https://github.com/yhdc/spring_cloud_msa/video_catalog_service"));
    }
}
```

## Server-to-Server Communication - Rest Client

For the communication between services, I have implemented RestClient.
This is a custom rest client which can be modified to fit for the multiple client services.

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

For example, below is a method for deleting a video file from the Video Stream server.
Video files are saved under file system in the Video Stream server which has to be deleted along with the video catalog.

```java
public class VideoFileRestClientService {

    private static final String VideoServerUrl = "lb://VIDEO-STREAM-SERVICE/video-stream";

    private final RestClient restClient;


    /**
     * DELETE SELECTED VIDEOS
     *
     * @param videoPathList
     */
    public ResponseEntity<?> deleteSelectedVideoFiles(List<String> videoPathList) {
        return restClient.delete()
                .uri(VideoServerUrl + "/delete/selected-video/{videoPathList}", videoPathList)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
                    } else if (response.getStatusCode().is5xxServerError()) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
                    }
                });
    }
}
```

> NOTE: gRpc protocol will be added as a comparison for the next major release version

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
info.app.name=Video Catalog Service
info.app.description=Video Catalog Management Service
info.app.version=1.0.0
info.app.author=Daniel Choi
```
