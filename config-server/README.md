# Config Server

> Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system.
> With
> the Config Server you have a central place to manage external properties for applications across all services.

<br/>

```properties
spring.application.name=config-server
server.port=8888
spring.profiles.active=native
```

> The configurations can be pulled from Backend File System or Git repo. For this project I have located the
> congiuration
> files under config server's resources directory.

<br/>

<img src="./readme/image/config-file-list.png" width="200" height="200"/>
