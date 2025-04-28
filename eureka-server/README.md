# Netflix Eureka Service Registry

> The Eureka is a service registry that allows microservices to register themselves and discover other services. In
> essence, it acts like a phone directory for your microservices, providing a mechanism for service-to-service discovery
> and registration.

```properties
eureka.instance.hostname=localhost
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

> I have the register and fetch settings to false since we don't want to register itself as a service.

```java

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

> The @EnableEurekaServer annotation is added to run the server as a service registry server.
