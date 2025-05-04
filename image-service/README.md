# Image Microservice

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
                .info(new Info().title("Image Service")
                        .description("REST API Docs for image service")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Image server Wiki")
                        .url("https://github.com/yhdc/spring_cloud_msa/image_service"));
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
info.app.name=Image Service
info.app.description=Image Management Service
info.app.version=1.0.0
info.app.author=Daniel Choi
```