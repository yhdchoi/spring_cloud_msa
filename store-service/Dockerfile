# Build Stage
FROM amazoncorretto:21-alpine-jdk AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew

RUN ./gradlew clean

RUN ./gradlew bootJar

# Runtime Stage
FROM amazoncorretto:21-alpine-jdk AS runtime

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]