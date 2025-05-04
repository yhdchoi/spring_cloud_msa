# Config Server

> Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system. With the Config Server you have a central place to manage external properties for applications across all services.

```properties
spring.application.name=config-server
server.port=8888
spring.profiles.active=native
```

> The configurations can be pulled from Backend File System or Git repo. For this project I have located the
> configuration

```properties
spring.cloud.config.server.git.uri=https://github.com/${CONFIG_GIT_REPO_URI}
spring.cloud.config.server.git.username=${CONFIG_GIT_UN}
spring.cloud.config.server.git.password=${CONFIG_GIT_PWD}
```

> files under config server's resources directory.

<img src="../readme/image/config-file-list.png" width="200" height="200"/>

